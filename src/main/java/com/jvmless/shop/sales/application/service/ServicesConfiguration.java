package com.jvmless.shop.sales.application.service;

import com.jvmless.shop.sales.application.service.ProductService;
import com.jvmless.shop.sales.domain.productcatalog.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }
}
