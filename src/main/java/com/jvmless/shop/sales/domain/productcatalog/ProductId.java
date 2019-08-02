package com.jvmless.shop.sales.domain.productcatalog;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Embeddable
public class ProductId implements Serializable {
    private String id;

    public static ProductId generate() {
        return null;
    }

    public static ProductId of(String id) {
        return new ProductId(id);
    }
}
