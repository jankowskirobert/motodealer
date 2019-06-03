package com.jvmless.shop.sales.application.command;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import lombok.Data;

@Data
public class ProductReservationCommand {
    private ProductId productId;
    private ReservationId reservationId;
}
