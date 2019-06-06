package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationRuleFactory {

    private UserRepository userRepository;
    private ReservationRepository reservationRepository;

    public ReservationPolicy generate(ReservationRule reservationRule) {
        switch (reservationRule) {
            case USERTYPE_MAX_RESERVATION_RULE:
                return new MaxReservationsPolicy(userRepository);
            case MULTIPLE_WITH_HISTORY:
                return new MultipleReservedPolicy(userRepository, reservationRepository);
            default:
                return (reservedItems, userId) -> false;
        }
    }
}
