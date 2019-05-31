package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;

import java.util.HashSet;
import java.util.Set;

public class Reservation {
    private ReservationId reservationId;
    private Set<ReservationItem> reservationItems = new HashSet<>();
    private ReservationRule reservationRule;
    private UserId userId;
    private ReservationStatus reservationStatus;

    protected Reservation(ReservationId reservationId, UserId userId) {
        this.reservationId = reservationId;
        this.userId = userId;
    }

    private void updateReservationRule(ReservationRule reservationRule) {
        this.reservationRule = reservationRule;
    }

    public boolean reserve(ReservationRuleFactory reservationRuleFactory) {
        ReservationPolicy reservationPolicy = reservationRuleFactory.generate(reservationRule);
        if(reservationPolicy.check(reservationItems, userId) && isActive()) {
            this.reservationItems.add(new ReservationItem());
            return true;
        }
        return false;
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
