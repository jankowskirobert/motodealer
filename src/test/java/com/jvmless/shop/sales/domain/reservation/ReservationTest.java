package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.usermanagement.InMemoryUserRepository;
import com.jvmless.shop.usermanagement.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class ReservationTest {

    UserRepository userRepository = new InMemoryUserRepository();
    ReservationRepository reservationRepository = new InMemoryReservationRepository();
    ReservationRuleFactory reservationRuleFactory = new ReservationRuleFactory(userRepository, reservationRepository);

    @Before
    public void setUp() {
        userRepository.save(new User(UserId.of("PREMIUM_USER"),new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM));
    }

    @Test
    public void shouldBeAvailableToReserve() {
        ProductId productId = ProductId.generate();
        Reservation reservation = new Reservation(ReservationId.of("RESERVATION_1"), UserId.of("USER_1"));
        reservation.reserve(productId, reservationRuleFactory);
    }
}
