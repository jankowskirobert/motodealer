package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;

import java.util.Set;

public class OnlyOneReservationByUserPolicy implements ProductReservationPolicy {

    @Override
    public boolean canReserve(UserId potentialOwner, Set<ProductReservationHistory> productReservationHistories) {
        return productReservationHistories.stream().anyMatch(x -> x.getPotentialOwner().equals(potentialOwner))
                && !productReservationHistories.stream().anyMatch(x -> x.isActive())
                ;
    }
}
