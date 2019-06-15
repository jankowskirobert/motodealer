package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.*;

import java.util.Set;

public class MaxReservationsPolicy implements ReservationPolicy {

    private final Set<ReservationItem> reservationItems;
    private final User potentialOwner;

    public MaxReservationsPolicy(Set<ReservationItem> reservationItems, User potentialOwner) {
        this.reservationItems = reservationItems;
        this.potentialOwner = potentialOwner;
    }

    @Override
    public boolean check() {
        if (potentialOwner != null)
            if (potentialOwner.is(UserType.STANDARD_BUYER)) {
                if (reservationItems.isEmpty() || reservationItems.size() < 3) {
                    return true;
                }
            } else if (potentialOwner.isTypeOrHigher(UserType.PREMIUM)) {
                return true;
            }
        return false;
    }
}
