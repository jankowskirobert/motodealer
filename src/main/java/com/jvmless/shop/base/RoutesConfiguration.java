package com.jvmless.shop.base;

import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import com.jvmless.shop.sales.application.route.ReservationRoute;
import com.jvmless.shop.sales.domain.productcatalog.ProductRepository;
import com.jvmless.shop.sales.domain.productcatalog.ProductReservationPolicyFactory;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationRuleFactory;
import com.jvmless.shop.usermanagement.UserContextService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {

    @Bean
    public ProductReservationCommandHandler productReservationCommandHandler(
            ProductRepository productRepository,
            UserContextService userContextService,
            ReservationRepository reservationRepository,
            ProductReservationPolicyFactory productReservationPolicyFactory,
            ReservationRuleFactory reservationRuleFactory

    ) {
        return new ProductReservationCommandHandler(productRepository,
                userContextService,
                reservationRepository,
                productReservationPolicyFactory,
                reservationRuleFactory
        );
    }

    @Bean
    public ReservationRoute reservationRoute(ProductReservationCommandHandler productReservationCommandHandler) {
        return new ReservationRoute(productReservationCommandHandler);
    }
}
