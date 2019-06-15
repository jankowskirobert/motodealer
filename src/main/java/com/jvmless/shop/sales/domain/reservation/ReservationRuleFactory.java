package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.User;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class ReservationRuleFactory {

    private UserRepository userRepository;

    public ReservationPolicy generate(ReservationRule reservationRule, Set<ReservationItem> reservationItems, UserId userData) {
        User potentialOwner = userRepository.find(userData);
        if (potentialOwner == null)
            throw new IllegalArgumentException("User not found!");
        switch (reservationRule) {
            case USERTYPE_MAX_RESERVATION_RULE:
                return new MaxReservationsPolicy(reservationItems, potentialOwner);
            case MULTIPLE_WITH_HISTORY:
                return new MultipleReservedPolicy(reservationItems, potentialOwner);
            default:
                return () -> false;
        }
    }
}
