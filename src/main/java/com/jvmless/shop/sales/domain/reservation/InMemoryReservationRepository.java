package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    @Override
    public List<Reservation> findByUserId(UserId userData) {
        return inMemory.entrySet().stream()
                .filter(
                        x -> x.getValue().getUserId().equals(userData)
                )
                .map(
                        y -> y.getValue()
                )
                .collect(
                        Collectors.toList()
                );
    }
}
