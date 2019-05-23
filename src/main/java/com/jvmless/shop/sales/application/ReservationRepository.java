package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.reservation.Reservation;
import com.jvmless.shop.sales.domain.reservation.ReservationId;

public interface ReservationRepository {
    Reservation find(ReservationId reservationId);
}
