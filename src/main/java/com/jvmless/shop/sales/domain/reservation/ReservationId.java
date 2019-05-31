package com.jvmless.shop.sales.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationId {
    private String id;

    public static ReservationId of(String reservationId) {
        return new ReservationId(reservationId);
    }
}
