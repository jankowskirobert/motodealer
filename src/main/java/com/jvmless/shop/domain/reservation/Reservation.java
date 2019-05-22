package com.jvmless.shop.domain.reservation;

import com.jvmless.shop.domain.productcatalog.UserId;

import java.util.Set;

public class Reservation {
    private ReservationId reservationId;
    private Set<ReservationItem> reservationItems;
    private UserId userData;
    private ReservationPolicy reservationPolicy; //niech polityka rezerwacji sobie sprawdzi czy user ma status vipa

    public void reserve() {
        if(reservationPolicy.check(reservationItems, userData)) {

        }
    }

}
