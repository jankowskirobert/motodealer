package com.jvmless.shop.sales.domain.productcatalog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MotorcycleTechnicalDetails {

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
