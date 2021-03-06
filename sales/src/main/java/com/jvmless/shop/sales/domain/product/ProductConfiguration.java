package com.jvmless.shop.sales.domain.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@Configuration
@Slf4j
public class ProductConfiguration {

    @Bean
    public ProductRepository productRepository(RDBProductRepository rdbProductRepository) {
        log.info("Creating RDB Product repository");
        return new RDBProductRepositoryAdapter(rdbProductRepository);
    }


}
