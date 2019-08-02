package com.jvmless.shop.sales.domain.productcatalog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@AllArgsConstructor
public class MotorcycleTechnicalDetailsId implements Serializable {
    private String id;

    public static MotorcycleTechnicalDetailsId generate() {
        return new MotorcycleTechnicalDetailsId(UUID.randomUUID().toString());
    }
}
