package com.jvmless.shop.sales.application.command;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductReservationCommand {
    @NotNull
    private ProductId productId;
    @NotNull
    private ReservationId reservationId;
}
