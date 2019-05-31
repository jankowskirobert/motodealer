package com.jvmless.shop.sales.domain.productcatalog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductId {
    private String id;

    public static ProductId generate() {
        return null;
    }

    public static ProductId of(String id) {
        return new ProductId(id);
    }
}
