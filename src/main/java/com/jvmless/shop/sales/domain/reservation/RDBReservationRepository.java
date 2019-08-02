package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RDBReservationRepository extends JpaRepository<Reservation, ReservationId> {
    List<Reservation> findAllByUserId(UserId userId);
}
