package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.sales.domain.productcatalog.UserId;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    @Test
    public void reserve() {
        Product product = new Product(ProductId.generate(), null);
        product.reserve(UserId.of("TEST"));
        boolean result = product.canBeReserved();
        Assert.assertFalse(result);
    }

    @Test
    public void reserve1() {
        Product product = new Product(ProductId.generate(), null);
        boolean result = product.canBeReserved();
        Assert.assertTrue(result);
    }

    @Test(expected = IllegalStateException.class)
    public void reserve2() {
        Product product = new Product(ProductId.generate(), null);
        boolean result = product.canBeReserved();
        Assert.assertTrue(result);
        product.reserve(UserId.of("TEST"));
        product.reserve(UserId.of("TEST"));
    }
}