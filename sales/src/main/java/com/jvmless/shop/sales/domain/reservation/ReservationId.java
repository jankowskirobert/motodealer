package com.jvmless.shop.sales.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Embeddable
public class ReservationId implements Serializable {
    private String id;

    public static ReservationId random() {
        return new ReservationId(UUID.randomUUID().toString());
    }

    public static ReservationId of(String reservationId) {
        return new ReservationId(reservationId);
    }
}
