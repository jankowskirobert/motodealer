package com.jvmless.shop.domain.reservation;

import com.jvmless.shop.domain.productcatalog.UserId;

import java.util.Set;

public interface ReservationPolicy {
    boolean check(Set<ReservationItem> reservationItems, UserId userData);
}
