package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;

import java.util.Set;

//Policy does not need to be represent as set because strategy pattern and
//it can encapsulate rules inside current strategy implementation
public interface ProductReservationPolicy {
    boolean canReserve(UserId potentialOwner, Set<ProductReservationHistory> productReservationHistories);
}
