package com.jvmless.shop.sales.domain.product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Embeddable
@NoArgsConstructor
public class ProductId implements Serializable {
    private String id = UUID.randomUUID().toString();

    public static ProductId generate() {
        return null;
    }

    public static ProductId of(String id) {
        return new ProductId(id);
    }
}
