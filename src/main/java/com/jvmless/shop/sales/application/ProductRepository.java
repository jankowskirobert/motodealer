package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.reservation.Product;

public interface ProductRepository {
    Product find(ProductId productId);
}
