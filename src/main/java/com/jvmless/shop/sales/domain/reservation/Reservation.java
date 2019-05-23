package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;

import java.util.Set;

public class Reservation {
    private ReservationId reservationId;
    private Set<ReservationItem> reservationItems;
    private UserId userId;
    private ReservationStatus reservationStatus;

    protected Reservation(UserId userId) {

    }

    /**
     *
     * @param reservationPolicy (cannot be saved as a entity field in storage, must be delivery from outside)
     */
    public void reserve(ReservationPolicy reservationPolicy) {
        if(reservationPolicy.check(reservationItems, userId) && isActive()) {
            this.reservationItems.add(new ReservationItem());
        }
    }

    public void close(){
        if (isClosed())
            throw new IllegalStateException("Already closed");
        this.reservationStatus = ReservationStatus.CLOSED;
    }

    private boolean isClosed() {
        return !isActive();
    }

    private boolean isActive() {
        return ReservationStatus.ACTIVE.equals(this.reservationStatus);
    }

}
