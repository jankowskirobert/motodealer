package com.jvmless.shop.domain.reservation;

import com.jvmless.shop.domain.productcatalog.UserId;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class ProductReservationHistory {
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private UserId potentialOwner;

    protected ProductReservationHistory(Period period, UserId potentialOwner) {
        reservationStart = LocalDateTime.now();
        reservationEnd = reservationStart.plus(period);
    }

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(reservationStart) && now.isBefore(reservationEnd);
    }

}
