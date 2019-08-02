package com.jvmless.shop.sales.domain.productcatalog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@AllArgsConstructor
public class MotorcycleTechnicalDetails {

    private String vin;
//    private TireSpecificationId tireSpecificationId;
//    @Id
    @EmbeddedId
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
