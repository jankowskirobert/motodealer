package com.jvmless.shop.sales.application.dto;

import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ReservedProductMessage {
    private String description;
    private List<ProductId> productId;
    private ReservationId reservationId;
}
