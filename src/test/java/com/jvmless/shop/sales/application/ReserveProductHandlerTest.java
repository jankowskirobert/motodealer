package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.InMemoryProductRepository;
import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.reservation.InMemoryReservationRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import com.jvmless.shop.usermanagement.InMemoryUserRepository;
import com.jvmless.shop.sales.domain.productcatalog.ProductRepository;
import com.jvmless.shop.sales.domain.productcatalog.ProductReservationPolicyFactory;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationRuleFactory;
import com.jvmless.shop.usermanagement.UserContextService;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import org.junit.Test;

public class ReserveProductHandlerTest {

    private UserRepository userRepository = new InMemoryUserRepository();
    private ProductRepository productRepository = new InMemoryProductRepository();
    private UserContextService userContextService = () -> UserId.of("TEST");
    private ReservationRepository reservationRepository = new InMemoryReservationRepository();
    private ProductReservationPolicyFactory productReservationPolicyFactory = new ProductReservationPolicyFactory(userRepository);
    private ReservationRuleFactory reservationRuleFactory = new ReservationRuleFactory(userRepository);

    ProductReservationCommandHandler productReservationCommandHandler
            = new ProductReservationCommandHandler(
                    productRepository
            , userContextService
            , reservationRepository
            , productReservationPolicyFactory
            , reservationRuleFactory
    );

    @Test
    public void shouldReserveProduct() {
        ProductReservationCommand productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(ProductId.of("PRODUCT_1"));
        productReservationCommand.setReservationId(ReservationId.of("RESERVATION_1"));
        productReservationCommandHandler.handle(productReservationCommand);
    }
}
