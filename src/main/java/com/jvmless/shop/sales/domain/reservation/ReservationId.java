package com.jvmless.shop.sales.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ReservationId {
    private String id;
    public static ReservationId random() {
        return new ReservationId(UUID.randomUUID().toString());
    }

    public static ReservationId of(String reservationId) {
        return new ReservationId(reservationId);
    }
}
