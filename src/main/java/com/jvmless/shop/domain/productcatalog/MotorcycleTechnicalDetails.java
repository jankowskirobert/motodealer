package com.jvmless.shop.domain.productcatalog;

import lombok.Getter;

@Getter
public class MotorcycleTechnicalDetails {

    private String vin;
//    private TireSpecificationId tireSpecificationId;
//    @Id
    private MotorcycleTechnicalDetailsId motorcycleTechnicalDetailsId;

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
