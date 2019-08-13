package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.reservation.Reservation;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import com.jvmless.shop.usermanagement.UserId;

import java.util.List;

public interface ReservationRepository {
    void save(Reservation reservation);

    Reservation find(ReservationId reservationId);

    void clear();

    List<Reservation> findByUserId(UserId userData);
}
