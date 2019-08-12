package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.User;

import java.util.Set;

public class MultipleReservedPolicy implements ReservationPolicy {

    private final Set<ReservationItem> reservationItems;
    private final User userData;

    public MultipleReservedPolicy(Set<ReservationItem> reservationItems, User userData) {
        this.reservationItems = reservationItems;
        this.userData = userData;
    }

    @Override
    public boolean check() {
//        reservationRepository.findByUserId(userData);
        //TODO: implement policy
        return false;
    }
}
