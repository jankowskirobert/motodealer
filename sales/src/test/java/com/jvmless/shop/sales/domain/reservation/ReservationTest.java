package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.usermanagement.InMemoryUserRepository;
import com.jvmless.shop.usermanagement.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class ReservationTest {

    private final UserId PREMIUM_USER_ID = UserId.of("PREMIUM_USER");

    @Test
    public void shouldBeAvailableToReserve() {
        ProductId productId = ProductId.generate();
        Reservation reservation = new Reservation(ReservationId.of("RESERVATION_1"), UserId.of("USER_1"));

        User potentialOwner = new User(PREMIUM_USER_ID, new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM);
        reservation.reserve(productId, new MaxReservationsPolicy(reservation.getReservationItems(), potentialOwner));
    }

    @Test
    public void testRemoveReservedItem() {
        ProductId productId = ProductId.generate();
        Reservation reservation = new Reservation(ReservationId.of("RESERVATION_1"), UserId.of("USER_1"));
        User potentialOwner = new User(PREMIUM_USER_ID, new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM);
        reservation.reserve(productId, new MaxReservationsPolicy(reservation.getReservationItems(), potentialOwner));
        Assert.assertTrue(reservation.contains(productId));
        reservation.cancelReservation(productId);
        Assert.assertFalse(reservation.contains(productId));
    }
}
