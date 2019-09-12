package com.jvmless.shop.sales.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservedProductMessage {
    private String description;
    private String productId;
    private String reservationId;
}
