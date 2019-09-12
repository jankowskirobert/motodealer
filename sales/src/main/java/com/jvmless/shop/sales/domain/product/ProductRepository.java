package com.jvmless.shop.sales.domain.product;

public interface ProductRepository {
    void save(Product product);

    Product find(ProductId productId);

    void clear();

}
