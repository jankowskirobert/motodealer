package com.jvmless.shop.sales.application.service;

import com.jvmless.shop.sales.application.dto.UpdateProductData;
import com.jvmless.shop.sales.domain.productcatalog.MotorcycleTechnicalDetails;
import com.jvmless.shop.sales.domain.productcatalog.Product;
import com.jvmless.shop.sales.domain.productcatalog.ProductRepository;

class ProductServiceImpl implements  ProductService{

    ProductRepository productRepository;

    @Override
    public void updateProductData(UpdateProductData updateProductData) {
        Product product = productRepository.find(updateProductData.getProductId());
        if(product != null ) {
            product.updateDetails(updateProductData.getMotorcycleData());
        }
    }
}
