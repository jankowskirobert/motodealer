package com.jvmless.shop.sales.domain.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class MotorcycleTechnicalDetails extends ProductDetail {

    private String vin;
//    private TireSpecificationId tireSpecificationId;
//    @Id

//    public static MotorcycleTechnicalDetails of(MotorcycleDetails motorcycleDetails) {
//        return null;
//    }

    public static MotorcycleTechnicalDetails empty() {
        return null;
    }
//
//    public void transmissionDetails(ChainTransmissionId chainTrnsmission) {
//
//    }
//
//    public void tireSpecification(TireSpecificationId tireSpecification) {
//
//    }


}
