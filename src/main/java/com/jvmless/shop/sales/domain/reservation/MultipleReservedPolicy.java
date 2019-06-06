package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;

import java.util.Set;

public class MultipleReservedPolicy implements ReservationPolicy {

    private UserRepository userRepository;
    private ReservationRepository reservationRepository;

    public MultipleReservedPolicy(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public boolean check(Set<ReservationItem> reservationItems, UserId userData) {
//        reservationRepository.findByUserId(userData);
        //TODO: implement policy
        return false;
    }
}
