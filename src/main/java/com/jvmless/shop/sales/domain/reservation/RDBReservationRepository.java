package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RDBReservationRepository extends JpaRepository<Reservation, ReservationId> {
    List<Reservation> findAllByUserId(UserId userId);
}
