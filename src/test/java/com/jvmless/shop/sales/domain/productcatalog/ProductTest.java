package com.jvmless.shop.sales.domain.productcatalog;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.usermanagement.InMemoryUserRepository;
import com.jvmless.shop.usermanagement.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class ProductTest {

    private UserRepository userRepository = new InMemoryUserRepository();
    private ProductReservationPolicyFactory productReservationPolicyFactory = new ProductReservationPolicyFactory(userRepository);

    @Before
    public void setUp() {
        userRepository.save(new User(UserId.of("PREMIUM_USER"),new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM));
    }

    @Test
    public void shouldBeUnavailableToReserve_currentlyReserved() {
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicyFactory);
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
        boolean available = product.isAvailable();
        Assert.assertTrue(available);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicyFactory);
        boolean reserved = product.isAvailable();
        Assert.assertFalse(reserved);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicyFactory);
        //error
    }

    @Test
    public void shouldSellProduct() {
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        product.sell(UserId.of("TEST_BUYER"));
    }
    @Test
    public void shouldSellProduct_reservationFirst() {
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicyFactory);
        product.sell(UserId.of("PREMIUM_USER"));
    }
}