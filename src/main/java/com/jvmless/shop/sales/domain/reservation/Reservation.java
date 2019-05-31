package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.usermanagement.UserId;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Reservation {
    @Id
    private ReservationId reservationId;
    private Set<ReservationItem> reservationItems = new HashSet<>();
    private ReservationRule reservationRule;
    private UserId userId;
    private ReservationStatus reservationStatus;

    protected Reservation(ReservationId reservationId, UserId userId) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.reservationRule = ReservationRule.ONLY_ONE;
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    private void updateReservationRule(ReservationRule reservationRule) {
        this.reservationRule = reservationRule;
    }

    public boolean reserve(ReservationRuleFactory reservationRuleFactory) {
        if(isActive()) {
            ReservationPolicy reservationPolicy = reservationRuleFactory.generate(reservationRule);
            if (reservationPolicy.check(reservationItems, userId)) {
                this.reservationItems.add(new ReservationItem());
                return true;
            }
        }
        return false;
    }

    public void close() {
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
