package com.jvmless.shop.sales.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddProductDto {
    @NotNull
    private String productId;
    private String reservationType;
}
