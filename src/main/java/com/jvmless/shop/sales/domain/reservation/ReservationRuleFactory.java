package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationRuleFactory {

    private UserRepository userRepository;

    public ReservationPolicy generate(ReservationRule reservationRule) {
        switch (reservationRule) {
            case ONLY_ONE:
                return new MaxReservationsPolicy(userRepository);
            case MULTIPLE_WITH_HISTORY:
                return (reservedItems, userId) -> false;
            default:
                return (reservedItems, userId) -> false;
        }
    }
}
