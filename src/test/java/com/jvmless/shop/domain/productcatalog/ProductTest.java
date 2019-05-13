package com.jvmless.shop.domain.productcatalog;

import com.jvmless.shop.sharedkernel.Money;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void changePrice() {
        Money oldPrice = new Money(10999);
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, oldPrice);
        Money newPrice = new Money(19999);
        sampleMotorcycle.changePrice(newPrice);
        Assert.assertEquals(newPrice, sampleMotorcycle.price());
    }

    @Test(expected = IllegalStateException.class)
    public void changePrice_wrongStatus() {
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, new Money(10999));
        sampleMotorcycle.reserve(UserId.of("new_owner"));
        sampleMotorcycle.changePrice(new Money(19999));
    }

    @Test
    public void reserve() {
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, new Money(10999));
        sampleMotorcycle.reserve(UserId.of("new_owner"));
        Assert.assertEquals(ProductStatus.RESERVED, sampleMotorcycle.status());
    }

    @Test(expected = IllegalStateException.class)
    public void reserve_wrongStatus() {
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, new Money(10999));
        sampleMotorcycle.sell(UserId.of("new_owner"));
        sampleMotorcycle.reserve(UserId.of("new_owner"));
    }

    @Test
    public void sell() {
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, new Money(10999));
        sampleMotorcycle.sell(UserId.of("new_owner"));
        Assert.assertEquals(ProductStatus.SOLD, sampleMotorcycle.status());
    }

    @Test
    public void sell_afterReservation() {
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, new Money(10999));
        sampleMotorcycle.reserve(UserId.of("someUser"));
        sampleMotorcycle.sell(UserId.of("someUser"));
        Assert.assertEquals(ProductStatus.SOLD, sampleMotorcycle.status());
    }

    @Test(expected = IllegalStateException.class)
    public void sell_wrongStatus() {
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, new Money(10999));
        sampleMotorcycle.sell(UserId.of("someUser"));
        sampleMotorcycle.sell(UserId.of("someOtherUser"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sell_wrongUser() {
        Product sampleMotorcycle = new Product(ProductId.generate(), MotorcycleTechnicalDetails.empty(), null, new Money(10999));
        sampleMotorcycle.reserve(UserId.of("someUser"));
        sampleMotorcycle.sell(UserId.of("someOtherUser"));
    }
}