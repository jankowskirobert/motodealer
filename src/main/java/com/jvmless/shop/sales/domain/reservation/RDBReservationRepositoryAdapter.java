package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;

import java.util.List;

public class RDBReservationRepositoryAdapter implements ReservationRepository {

    private final RDBReservationRepository rdbReservationRepository;

    public RDBReservationRepositoryAdapter(RDBReservationRepository rdbReservationRepository) {
        this.rdbReservationRepository = rdbReservationRepository;
    }

    @Override
    public void save(Reservation reservation) {
        rdbReservationRepository.save(reservation);
    }

    @Override
    public Reservation find(ReservationId reservationId) {
        return rdbReservationRepository.getOne(reservationId);
    }

    @Override
    public void clear() {
        rdbReservationRepository.deleteAll();
    }

    @Override
    public List<Reservation> findByUserId(UserId userData) {
        return rdbReservationRepository.findAllByUserId(userData);
    }
}
