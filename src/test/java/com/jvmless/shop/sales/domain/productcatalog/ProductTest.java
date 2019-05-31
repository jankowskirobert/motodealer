package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.sales.domain.FakeUserRepository;
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
    private ProductReservationPolicyFactory productReservationPolicyFactory = new ProductReservationPolicyFactory(userRepository);

    @Before
    public void setUp() {
        userRepository.save(new User(UserId.of("PREMIUM_USER"), new HashSet<>(Arrays.asList(UserRole.PREMIUM))));
    }

    @Test
    public void shouldBeUnavailableToReserve_currentlyReserved() {
        Product product = new Product(ProductId.generate(), null, ProductReservationPolicyType.ONLY_PREMIUM);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicyFactory);
        boolean result = product.isAvailable();
        Assert.assertFalse(result);
    }

    @Test
    public void shouldBeAvailableToReserve_defaultProductCreation() {
        Product product = new Product(ProductId.generate(), null);
        boolean result = product.isAvailable();
        Assert.assertTrue(result);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionOnReservation_productAlreadyReserved() {
        Product product = new Product(ProductId.generate(), null, ProductReservationPolicyType.ONLY_PREMIUM);
        boolean available = product.isAvailable();
        Assert.assertTrue(available);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicyFactory);
        boolean reserved = product.isAvailable();
        Assert.assertFalse(reserved);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicyFactory);
        //error
    }
}