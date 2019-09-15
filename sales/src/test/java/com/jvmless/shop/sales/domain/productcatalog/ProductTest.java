package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.sales.domain.product.*;
import com.jvmless.shop.usermanagement.InMemoryUserRepository;
import com.jvmless.shop.usermanagement.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class ProductTest {

    @Test
    public void shouldBeUnavailableToReserve_currentlyReserved() {
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        UserId premium_user = UserId.of("PREMIUM_USER");
        User premiumUser = new User(premium_user, new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM);
        OnlyPremium onlyPremium = new OnlyPremium(premiumUser);
        product.reserve(premium_user, onlyPremium);
        boolean result = product.isAvailable();
        Assert.assertFalse(result);
    }

    @Test
    public void shouldBeAvailableToReserve_defaultProductCreation() {
        Product product = new Product(ProductId.generate());
        boolean result = product.isAvailable();
        Assert.assertTrue(result);
    }

    @Test(expected = DomainException.class)
    public void shouldThrowExceptionOnReservation_productAlreadyReserved() {
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        UserId premium_user = UserId.of("PREMIUM_USER");
        User premiumUser = new User(premium_user, new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM);
        OnlyPremium onlyPremium = new OnlyPremium(premiumUser);
        boolean available = product.isAvailable();
        Assert.assertTrue(available);
        product.reserve(premium_user, onlyPremium);
        boolean reserved = product.isAvailable();
        Assert.assertFalse(reserved);
        product.reserve(premium_user, onlyPremium);
        //error
    }

    @Test
    public void shouldSellProduct() {
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        product.sell(UserId.of("TEST_BUYER"));
    }

    @Test
    public void shouldSellProduct_reservationFirst() {
        UserId premium_user = UserId.of("PREMIUM_USER");
        User premiumUser = new User(premium_user, new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM);
        OnlyPremium onlyPremium = new OnlyPremium(premiumUser);
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        product.reserve(premium_user, onlyPremium);
        product.sell(premium_user);
    }
}