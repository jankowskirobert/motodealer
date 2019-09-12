package com.jvmless.shop.base;

import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import com.jvmless.shop.sales.application.route.ReservationRoute;
import com.jvmless.shop.sales.application.route.UserRoute;
import com.jvmless.shop.sales.domain.product.ProductConfiguration;
import com.jvmless.shop.sales.domain.product.ProductRepository;
import com.jvmless.shop.sales.domain.product.ProductReservationPolicyFactory;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import com.jvmless.shop.sales.domain.reservation.ReservationRuleFactory;
import com.jvmless.shop.usermanagement.UserRepository;
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
            UserRepository userRepository,
            ReservationRepository reservationRepository
    ) {
        return new ProductReservationCommandHandler(productRepository,
                reservationRepository,
                new ProductReservationPolicyFactory(userRepository),
                new ReservationRuleFactory(userRepository)
        );
    }

    @Bean
    public UserRoute userRoute() {
        return new UserRoute();
    }

    @Bean
    public ReservationRoute reservationRoute(ProductReservationCommandHandler productReservationCommandHandler) {
        return new ReservationRoute(productReservationCommandHandler);
    }
}
