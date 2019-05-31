package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.*;

import java.util.Set;

public class MaxReservationsPolicy implements ReservationPolicy{

    private final UserRepository userRepository;

    public MaxReservationsPolicy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean check(Set<ReservationItem> reservationItems, UserId userId) {
        User potentialOwner = userRepository.find(userId);
        if(potentialOwner.is(UserType.STANDARD_BUYER)) {
            if(reservationItems.isEmpty() || reservationItems.size() < 3){
                return true;
            }
        }
        else if(potentialOwner.isTypeOrHigher(UserType.PREMIUM)) {
            return true;
        }
        return false;
    }
}
