package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.UserId;
import com.jvmless.shop.usermanagement.User;
import com.jvmless.shop.usermanagement.UserRepository;
import com.jvmless.shop.usermanagement.UserRole;

import java.util.Set;

public class MaxReservationsPolicy implements ReservationPolicy{

    private UserRepository userRepository;

    @Override
    public boolean check(Set<ReservationItem> reservationItems, UserId userId) {
        User potentialOwner = userRepository.find(userId);
        if(potentialOwner.hasRole(UserRole.STANDARD_BUYER)) {
            if(reservationItems.isEmpty() || reservationItems.size() < 3){
                return true;
            }
        }
        return false;
    }
}
