package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.usermanagement.UserId;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
public class Reservation {
    @Id
    private ReservationId reservationId;
    private Set<ReservationItem> reservationItems = new HashSet<>();
    private ReservationRule reservationRule;
    private UserId userId;
    private ReservationStatus reservationStatus;

    public Reservation(ReservationId reservationId, UserId userId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.reservationRule = ReservationRule.ONLY_ONE;
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    private void updateReservationRule(ReservationRule reservationRule) {
        this.reservationRule = reservationRule;
    }

    public void reserve(ProductId productId, ReservationRuleFactory reservationRuleFactory) {
        if (isActive() && !contains(productId)) {
            ReservationPolicy reservationPolicy = reservationRuleFactory.generate(reservationRule);
            if (reservationPolicy.check(reservationItems, userId)) {
                this.reservationItems.add(
                        new ReservationItem(productId
                                , LocalDateTime.now()
                        )
                );
            }
        } else {
            throw new IllegalStateException("Cannot reserve, " +
                    "you already reserved this product or reservation is inactive");
        }
    }

    public void close() {
        if (isClosed())
            throw new IllegalStateException("Already closed");
        this.reservationStatus = ReservationStatus.CLOSED;
    }

    public boolean isClosed() {
        return !isActive();
    }

    public boolean isActive() {
        return ReservationStatus.ACTIVE.equals(this.reservationStatus);
    }

    public boolean contains(ProductId productId) {
        return reservationItems.stream().anyMatch(x -> x.getProductId().equals(productId));
    }
}
