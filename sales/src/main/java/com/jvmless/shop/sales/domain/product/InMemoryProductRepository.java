package com.jvmless.shop.sales.domain.product;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    ConcurrentHashMap<ProductId, Product> inMemory = new ConcurrentHashMap<>();

    @Override
    public void save(Product product) {
        inMemory.put(product.productId(), product);
    }

    @Override
    public Product find(ProductId productId) {
        return inMemory.get(productId);
    }

    @Override
    public void clear() {
        inMemory.clear();
    }
}
