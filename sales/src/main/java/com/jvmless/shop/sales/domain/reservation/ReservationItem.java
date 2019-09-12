package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "productId")
@Entity
public class ReservationItem {
    @EmbeddedId
    private ProductId productId;
    private LocalDateTime productReservationDate;
}
