package com.jvmless.shop.sales.application.command;

import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RemoveProductReservationCommand {
    private ReservationId reservationId;
    private ProductId productId;
}
