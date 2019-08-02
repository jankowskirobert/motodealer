package com.jvmless.shop.base;

import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import com.jvmless.shop.sales.application.route.ReservationRoute;
import com.jvmless.shop.sales.domain.productcatalog.ProductConfiguration;
import com.jvmless.shop.sales.domain.productcatalog.ProductRepository;
import com.jvmless.shop.sales.domain.productcatalog.ProductReservationPolicyFactory;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationRuleFactory;
import com.jvmless.shop.usermanagement.UserContextService;
import com.jvmless.shop.usermanagement.UserRepository;
import org.apache.camel.spi.RestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Import(
        value = {ProductConfiguration.class}
)
@EnableTransactionManagement
@ComponentScans(value =
        {
                @ComponentScan(value = {"com.jvmless.shop"})
        })
@Configuration
public class RoutesConfiguration {

    @Bean
    public ProductReservationCommandHandler productReservationCommandHandler(
            ProductRepository productRepository,
            UserContextService userContextService,
            UserRepository userRepository,
            ReservationRepository reservationRepository
    ) {
        return new ProductReservationCommandHandler(productRepository,
                userContextService,
                reservationRepository,
                new ProductReservationPolicyFactory(userRepository),
                new ReservationRuleFactory(userRepository)
        );
    }

    @Bean
    public ReservationRoute reservationRoute(ProductReservationCommandHandler productReservationCommandHandler) {
        return new ReservationRoute(productReservationCommandHandler);
    }
}
