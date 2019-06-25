package com.jvmless.shop.sales.domain.reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationConfiguration {

    @Bean
    public ReservationRepository reservationRepository(RDBReservationRepository rdbReservationRepository) {
        return new RDBReservationRepositoryAdapter(rdbReservationRepository);
    }
}
