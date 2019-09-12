package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import com.jvmless.shop.sales.domain.product.*;
import com.jvmless.shop.sales.domain.reservation.*;
import com.jvmless.shop.usermanagement.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class AcceptanceReserveProductHandlerTest {

    private final ProductId PRODUCT_ID = ProductId.of("PRODUCT_1");
    private final UserId USER_ID = UserId.of("TEST");
    private final ReservationId RESERVATION_ID = ReservationId.of("RESERVATION_1");
    private InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private ProductRepository productRepository = new InMemoryProductRepository();
    private ReservationRepository reservationRepository = new InMemoryReservationRepository();
    private ProductReservationPolicyFactory productReservationPolicyFactory = new ProductReservationPolicyFactory(userRepository);
    private ReservationRuleFactory reservationRuleFactory = new ReservationRuleFactory(userRepository);


    @Before
    public void setUp() {
        productRepository.save(
                new Product(
                        PRODUCT_ID
                        , ProductReservationPolicyType.ONLY_PREMIUM
                )
        );

        userRepository.save(
                new User(
                        USER_ID
                        , new HashSet<>(Arrays.asList(UserRole.CLIENT))
                        , UserType.PREMIUM
                )
        );

        reservationRepository.save
                (new Reservation(
                                RESERVATION_ID
                                , USER_ID
                        )
                );
    }

    @Test
    public void shouldReserveProduct() {
        UserContextService userContextService = () -> USER_ID;
        ProductReservationCommandHandler productReservationCommandHandler
                = new ProductReservationCommandHandler(
                productRepository
                , userContextService
                , reservationRepository
                , productReservationPolicyFactory
                , reservationRuleFactory
        );
        ProductReservationCommand productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(PRODUCT_ID);
        productReservationCommand.setReservationId(RESERVATION_ID);
        productReservationCommandHandler.handle(productReservationCommand);
        Product product = productRepository.find(PRODUCT_ID);
        Reservation reservation = reservationRepository.find(RESERVATION_ID);
        Assert.assertTrue(reservation.contains(PRODUCT_ID));
        Assert.assertTrue(product.isReserved());
    }
}
