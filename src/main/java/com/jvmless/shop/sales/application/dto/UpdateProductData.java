package com.jvmless.shop.sales.application.dto;

import com.jvmless.shop.sales.domain.productcatalog.MotorcycleTechnicalDetails;
import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

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
