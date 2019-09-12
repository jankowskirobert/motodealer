package com.jvmless.shop.sales.application.command;

import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import com.jvmless.shop.usermanagement.UserId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductReservationCommand {
    @NotNull
    private List<ProductId> productId;
    @NotNull
    private ReservationId newReservationId;
    @NotNull
    private UserId userId;
}
