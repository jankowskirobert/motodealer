package com.jvmless.shop.sales.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RDBProductRepository extends JpaRepository<Product, ProductId> {
}
