package com.jvmless.shop.sales.domain.reservation;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryReservationRepository implements ReservationRepository {

    ConcurrentHashMap<ReservationId, Reservation> inMemory = new ConcurrentHashMap<>();

    @Override
    public void save(Reservation reservation) {
        inMemory.put(reservation.getReservationId(), reservation);
    }

    @Override
    public Reservation find(ReservationId reservationId) {
        return inMemory.get(reservationId);
    }

    @Override
    public void clear() {
        inMemory.clear();
    }
}
