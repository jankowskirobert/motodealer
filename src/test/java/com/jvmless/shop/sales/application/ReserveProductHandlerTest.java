package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.*;
import com.jvmless.shop.sales.domain.reservation.InMemoryReservationRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import com.jvmless.shop.usermanagement.InMemoryUserRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationRuleFactory;
import com.jvmless.shop.usermanagement.UserContextService;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import org.junit.Before;
import org.junit.Test;

public class ReserveProductHandlerTest {

    private UserRepository userRepository = new InMemoryUserRepository();
    private ProductRepository productRepository = new InMemoryProductRepository();
    private ReservationRepository reservationRepository = new InMemoryReservationRepository();
    private ProductReservationPolicyFactory productReservationPolicyFactory = new ProductReservationPolicyFactory(userRepository);
    private ReservationRuleFactory reservationRuleFactory = new ReservationRuleFactory(userRepository);


    @Before
    public void setUp() {
        productRepository.save(
                new Product(
                        ProductId.of("PRODUCT_1")
                        , UserId.of("TEST")
                        , ProductReservationPolicyType.ONLY_PREMIUM)
        );
    }

    @Test
    public void shouldReserveProduct() {
        UserContextService userContextService = () -> UserId.of("TEST");
        ProductReservationCommandHandler productReservationCommandHandler
                = new ProductReservationCommandHandler(
                productRepository
                , userContextService
                , reservationRepository
                , productReservationPolicyFactory
                , reservationRuleFactory
        );
        ProductReservationCommand productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(ProductId.of("PRODUCT_1"));
        productReservationCommand.setReservationId(ReservationId.of("RESERVATION_1"));
        productReservationCommandHandler.handle(productReservationCommand);
    }
}
