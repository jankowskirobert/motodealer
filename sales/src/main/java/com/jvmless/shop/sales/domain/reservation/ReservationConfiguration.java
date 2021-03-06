package com.jvmless.shop.sales.domain.reservation;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan
public class ReservationConfiguration {

    @Bean
    public ReservationRepository reservationRepository(RDBReservationRepository rdbReservationRepository) {
        return new RDBReservationRepositoryAdapter(rdbReservationRepository);
    }
}
