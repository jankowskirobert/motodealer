package com.jvmless.shop.sales.application.command;

import com.jvmless.shop.sales.domain.reservation.ReservationId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CancelReservationCommand {
    private ReservationId reservationId;
}
