package com.jvmless.shop.sales.application.dto;

import com.jvmless.shop.sales.domain.product.MotorcycleTechnicalDetails;
import com.jvmless.shop.sales.domain.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductData {
    private ProductId productId;
    private String vin;
    private int mileage;

    public MotorcycleTechnicalDetails getMotorcycleData() {
        return null;
    }
}
