package com.jvmless.shop.sales.application.service;

import com.jvmless.shop.sales.application.dto.AddProductDto;
import com.jvmless.shop.sales.application.dto.UpdateProductData;

public interface ProductService {
    void updateProductData(UpdateProductData motorcycleTechnicalDetails);

    void addProduct(AddProductDto addProductDto);
}
