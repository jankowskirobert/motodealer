package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.productcatalog.Product;

public interface ProductRepository {
    void save(Product product);
    Product find(ProductId productId);
}
