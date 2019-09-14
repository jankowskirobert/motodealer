package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "productId")
@Entity
public class ReservationItem {
    @EmbeddedId
    private ProductId productId;
    private LocalDateTime productReservationDate;
    private LocalDateTime productReservationDisableDate;
    private ReservationItemStatus reservationItemStatus;

    public ReservationItem(ProductId productId) {
        this.productId = productId;
        this.productReservationDate = LocalDateTime.now();
        this.reservationItemStatus = ReservationItemStatus.ACTIVE;
    }

    public void deactivate() {
        this.reservationItemStatus = ReservationItemStatus.CLOSED;
        this.productReservationDisableDate = LocalDateTime.now();
    }

    public boolean isActive() {
        return ReservationItemStatus.ACTIVE.equals(this.reservationItemStatus);
    }
}
