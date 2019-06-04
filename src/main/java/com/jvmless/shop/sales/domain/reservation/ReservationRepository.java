package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.reservation.Reservation;
import com.jvmless.shop.sales.domain.reservation.ReservationId;

public interface ReservationRepository {
    void save(Reservation reservation);
    Reservation find(ReservationId reservationId);

    void clear();
}
