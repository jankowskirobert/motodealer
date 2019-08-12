package com.jvmless.shop.sales.domain.productcatalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RDBProductRepository extends JpaRepository<Product, ProductId> {
}
