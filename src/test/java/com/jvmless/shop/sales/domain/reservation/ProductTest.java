package com.jvmless.shop.sales.domain.reservation;

import com.jvmless.shop.sales.domain.productcatalog.ProductId;
import com.jvmless.shop.usermanagement.User;
import com.jvmless.shop.usermanagement.UserId;
import com.jvmless.shop.usermanagement.UserRepository;
import com.jvmless.shop.usermanagement.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class ProductTest {

    private UserRepository userRepository = new FakeUserRepository();

    @Before
    public void setUp() {
        userRepository.save(new User(UserId.of("PREMIUM_USER"), new HashSet<>(Arrays.asList(UserRole.PREMIUM))));
    }

    @Test
    public void reserve() {
        OnlyPremium onlyPremium = new OnlyPremium(userRepository);
        Product product = new Product(ProductId.generate(), null);
        product.reserve(UserId.of("PREMIUM_USER"), onlyPremium);
        boolean result = product.isAvailable();
        Assert.assertFalse(result);
    }

    @Test
    public void reserve1() {
        OnlyPremium onlyPremium = new OnlyPremium(userRepository);
        Product product = new Product(ProductId.generate(), null);
        boolean result = product.isAvailable();
        Assert.assertTrue(result);
    }

    @Test(expected = IllegalStateException.class)
    public void reserve2() {
        OnlyPremium onlyPremium = new OnlyPremium(userRepository);
        Product product = new Product(ProductId.generate(), null);
        boolean result = product.isAvailable();
        Assert.assertTrue(result);
        product.reserve(UserId.of("PREMIUM_USER"), onlyPremium);
        product.reserve(UserId.of("PREMIUM_USER"), onlyPremium);
    }
}