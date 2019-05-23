package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;

import java.util.Set;

public interface ReservationPolicy {
    boolean check(Set<ReservationItem> reservationItems, UserId userData);
}
