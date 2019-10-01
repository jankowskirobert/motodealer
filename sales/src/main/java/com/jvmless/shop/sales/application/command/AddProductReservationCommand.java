package com.jvmless.shop.sales.application.command;

import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import com.jvmless.shop.usermanagement.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddProductReservationCommand {
    @NotNull
    private List<ProductId> productId;
    @NotNull
    private ReservationId reservationId;
    @NotNull
    private UserId userId; //POMYSLEC  bo moze jedna per user
}
