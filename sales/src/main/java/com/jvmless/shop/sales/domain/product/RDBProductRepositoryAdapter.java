package com.jvmless.shop.sales.domain.product;

import org.springframework.beans.factory.annotation.Autowired;

class RDBProductRepositoryAdapter implements ProductRepository {

    private final RDBProductRepository productRepository;

    @Autowired
    public RDBProductRepositoryAdapter(RDBProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product find(ProductId productId) {
        return productRepository.getOne(productId);
    }

    @Override
    public void clear() {
        productRepository.deleteAll();
    }
}
