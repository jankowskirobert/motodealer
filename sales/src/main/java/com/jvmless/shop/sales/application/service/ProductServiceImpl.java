package com.jvmless.shop.sales.application.service;

import com.jvmless.shop.sales.application.dto.AddProductDto;
import com.jvmless.shop.sales.application.dto.UpdateProductData;
import com.jvmless.shop.sales.domain.product.Product;
import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.sales.domain.product.ProductRepository;
import com.jvmless.shop.sales.domain.product.ProductReservationPolicyType;
import org.springframework.util.StringUtils;

class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void updateProductData(UpdateProductData updateProductData) {
        Product product = productRepository.find(updateProductData.getProductId());

    }

    @Override
    public void addProduct(AddProductDto addProductDto) {
        ProductId productId = ProductId.of(addProductDto.getProductId());
        Product newProduct = new Product(productId);
        if (!StringUtils.isEmpty(addProductDto.getReservationType())) {
            newProduct.updateReservationPoicy(ProductReservationPolicyType.valueOf(addProductDto.getReservationType()));
        }
        productRepository.save(newProduct);
    }
}
