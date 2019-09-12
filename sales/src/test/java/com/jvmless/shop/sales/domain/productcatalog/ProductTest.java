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

    private InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private ProductReservationPolicyFactory productReservationPolicyFactory = new ProductReservationPolicyFactory(userRepository);

    @Before
    public void setUp() {
        userRepository.save(new User(UserId.of("PREMIUM_USER"), new HashSet<UserRole>(Arrays.asList(UserRole.CLIENT)), UserType.PREMIUM));
    }

    @Test
    public void shouldBeUnavailableToReserve_currentlyReserved() {
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        UserId premium_user = UserId.of("PREMIUM_USER");
        ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(ProductReservationPolicyType.ONLY_PREMIUM, premium_user);
        product.reserve(premium_user, productReservationPolicy);
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
        ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(ProductReservationPolicyType.ONLY_PREMIUM, premium_user);
        boolean available = product.isAvailable();
        Assert.assertTrue(available);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicy);
        boolean reserved = product.isAvailable();
        Assert.assertFalse(reserved);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicy);
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
        ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(ProductReservationPolicyType.ONLY_PREMIUM, premium_user);
        Product product = new Product(ProductId.generate(), ProductReservationPolicyType.ONLY_PREMIUM);
        product.reserve(UserId.of("PREMIUM_USER"), productReservationPolicy);
        product.sell(UserId.of("PREMIUM_USER"));
    }
}