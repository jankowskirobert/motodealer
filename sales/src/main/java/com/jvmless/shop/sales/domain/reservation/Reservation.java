package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.usermanagement.UserId;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Entity
public class Reservation implements Serializable {
    @EmbeddedId
    private ReservationId reservationId;
    @OneToMany
    @JoinColumn(name = "itemId")
    private Set<ReservationItem> reservationItems = new HashSet<>();
    private ReservationRule reservationRule;
    private UserId userId;
    private ReservationStatus reservationStatus;

    public Reservation(ReservationId reservationId, UserId userId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.reservationRule = ReservationRule.USERTYPE_MAX_RESERVATION_RULE;
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    private void updateReservationRule(ReservationRule reservationRule) {
        this.reservationRule = reservationRule;
    }

    public void reserve(ProductId productId, ReservationPolicy reservationPolicy) {
        if (isActive() && !contains(productId)) {
            if (reservationPolicy.check()) {
                this.reservationItems.add(new ReservationItem(productId));
            }
        } else {
            throw new DomainException("Cannot reserve, " +
                    "you already reserved this product or reservation is inactive");
        }
    }

    public void close() {
        if (isClosed())
            throw new IllegalStateException("Already closed");
        this.reservationStatus = ReservationStatus.CLOSED;
    }

    public void cancelReservation(ProductId productId) {
        if (contains(productId) && isActive()) {
            Optional<ReservationItem> reservationItemOptional = reservationItems.stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst();
            reservationItemOptional.ifPresent(reservationItem -> {
                if (reservationItem.getReservationItemStatus().equals(ReservationItemStatus.ACTIVE)) {
                    reservationItem.deactivate();
                } else {
                    throw new DomainException("Currently item is not reserved by user");
                }
            });
        }
    }

    public boolean isClosed() {
        return !isActive();
    }

    public boolean isActive() {
        return ReservationStatus.ACTIVE.equals(this.reservationStatus);
    }

    public boolean contains(ProductId productId) {
        return reservationItems.stream().anyMatch(x -> productId.equals(x.getProductId()) && x.isActive());
    }
}
