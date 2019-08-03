package com.jvmless.shop.sales.application.api;

import com.jvmless.shop.sales.application.dto.AddProductDto;
import com.jvmless.shop.sales.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {


    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public void addProduct(@RequestBody AddProductDto addProductDto) {
        productService.addProduct(addProductDto);
    }

}
