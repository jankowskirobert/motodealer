package com.jvmless.shop.sales.application;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.sales.application.command.AddProductReservationCommand;
import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.application.handler.AddProductReservationCommandHandler;
import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import com.jvmless.shop.sales.domain.product.*;
import com.jvmless.shop.sales.domain.reservation.*;
import com.jvmless.shop.usermanagement.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RunWith(JUnit4.class)
public class ReserveProductHandlerTest {

    private final ProductId PRODUCT_ID_ALL = ProductId.of("PRODUCT_ALL");
    private final ProductId PRODUCT_ID_R1 = ProductId.generate();
    private final ProductId PRODUCT_ID_PREMIUM = ProductId.of("PRODUCT_PREMIUM");

    private final UserId USER_ID_STANDARD = UserId.of("TEST_STANDARD");
    private final UserId USER_ID_PREMIUM = UserId.of("TEST_PREMIUM");

    private final ReservationId RESERVATION_ID = ReservationId.of("RESERVATION_1");
    private InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private ProductRepository productRepository = new InMemoryProductRepository();
    private ReservationRepository reservationRepository = new InMemoryReservationRepository();
    private ProductReservationPolicyFactory productReservationPolicyFactory = new ProductReservationPolicyFactory(userRepository);
    private ReservationRuleFactory reservationRuleFactory = new ReservationRuleFactory(userRepository);


    @Before
    public void setUp() {
        productRepository.save(
                new Product(
                        PRODUCT_ID_PREMIUM
                        , ProductReservationPolicyType.ONLY_PREMIUM
                )
        );
        productRepository.save(
                new Product(
                        PRODUCT_ID_R1
                        , ProductReservationPolicyType.ALL
                )
        );
        productRepository.save(
                new Product(
                        PRODUCT_ID_ALL
                        , ProductReservationPolicyType.ALL
                )
        );
        userRepository.save(
                new User(
                        USER_ID_STANDARD
                        , new HashSet<>(Arrays.asList(UserRole.CLIENT))
                        , UserType.STANDARD_BUYER
                )
        );

        userRepository.save(
                new User(
                        USER_ID_PREMIUM
                        , new HashSet<>(Arrays.asList(UserRole.CLIENT))
                        , UserType.PREMIUM
                )
        );
    }

    @After
    public void after() {
        productRepository.clear();
        userRepository.clear();
        reservationRepository.clear();
    }

    @Test(expected = DomainException.class)
    public void shouldReserveProduct() {
//        UserContextService userContextService = () -> USER_ID_PREMIUM;
        ProductReservationCommandHandler productReservationCommandHandler = getProductReservationCommandHandler();
        ProductReservationCommand productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(Collections.singletonList(PRODUCT_ID_ALL));
        productReservationCommand.setUserId(USER_ID_PREMIUM);
        ReservationId random = ReservationId.random();
        productReservationCommand.setNewReservationId(random);
        productReservationCommandHandler.handle(productReservationCommand);
        Product product = productRepository.find(PRODUCT_ID_ALL);
        Assert.assertTrue(product.isReserved());
//        userContextService = () -> USER_ID_STANDARD;

        productReservationCommandHandler = getProductReservationCommandHandler();
        productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(Collections.singletonList(PRODUCT_ID_ALL));
        productReservationCommand.setUserId(USER_ID_STANDARD);
        ReservationId random2 = ReservationId.random();
        productReservationCommand.setNewReservationId(random2);
        productReservationCommandHandler.handle(productReservationCommand);
    }

    @Test()
    public void shouldReserveProduct2() {
        ProductReservationCommandHandler productReservationCommandHandler = getProductReservationCommandHandler();
        ProductReservationCommand productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(Collections.singletonList(PRODUCT_ID_ALL));
        ReservationId random = ReservationId.random();
        productReservationCommand.setNewReservationId(random);
        productReservationCommand.setUserId(USER_ID_PREMIUM);
        productReservationCommandHandler.handle(productReservationCommand);
        Assert.assertNotNull(random);
        Reservation reservation = reservationRepository.find(random);
        Assert.assertTrue(reservation.isActive());
        Product product = productRepository.find(PRODUCT_ID_ALL);
        Assert.assertTrue(product.isReserved());
    }

    @Test()
    public void silmul() {
        ProductReservationCommandHandler productReservationCommandHandler = getProductReservationCommandHandler();

        List<ProductId> productId = Collections.singletonList(PRODUCT_ID_ALL);
        ReservationId random = ReservationId.random();
        UserId user_id_premium = USER_ID_PREMIUM;

        ProductReservationCommand productReservationCommand1 = getProductReservationCommand(productId, random, user_id_premium);

        /* first user reserve product */
        productReservationCommandHandler.handle(productReservationCommand1);

        /* validate that it is reserved */
        Reservation reservation = reservationRepository.find(random);
        Assert.assertTrue(reservation.isActive());
        Product product = productRepository.find(PRODUCT_ID_ALL);
        Assert.assertTrue(product.isReserved());

        /* other user trying to reserve product */
        UserId user_id_standard = USER_ID_STANDARD;
        ProductReservationCommand productReservationCommand2 = getProductReservationCommand(productId, random, user_id_standard);
        productReservationCommandHandler.handle(productReservationCommand2);
    }

    @Test()
    public void silmul2() {
        ProductReservationCommandHandler productReservationCommandHandler = getProductReservationCommandHandler();

        List<ProductId> productId = Collections.singletonList(PRODUCT_ID_ALL);
        ReservationId random = ReservationId.random();
        UserId user_id_premium = USER_ID_PREMIUM;

        ProductReservationCommand productReservationCommand1 = getProductReservationCommand(productId, random, user_id_premium);

        /* first user reserve product */
        productReservationCommandHandler.handle(productReservationCommand1);

        /* validate that it is reserved */
        Reservation reservation = reservationRepository.find(random);
        Assert.assertTrue(reservation.isActive());
        Product product = productRepository.find(PRODUCT_ID_ALL);
        Assert.assertTrue(product.isReserved());

        /* same user trying to reserve product */
        UserId user_id_standard = USER_ID_PREMIUM;
        ProductReservationCommand productReservationCommand2 = getProductReservationCommand(productId, random, user_id_standard);
        productReservationCommandHandler.handle(productReservationCommand2);
    }

    @Test()
    public void silmul3() {
        AddProductReservationCommandHandler addProductReservationCommandHandler = addProductReservationCommandHandler();

        ProductReservationCommandHandler productReservationCommandHandler = getProductReservationCommandHandler();

        List<ProductId> productId1 = Collections.singletonList(PRODUCT_ID_ALL);
        List<ProductId> productId2 = Collections.singletonList(PRODUCT_ID_R1);
        ReservationId random = ReservationId.random();
        UserId user_id_premium = USER_ID_PREMIUM;

        ProductReservationCommand productReservationCommand1 = getProductReservationCommand(productId1, random, user_id_premium);

        /* first user reserve product */
        productReservationCommandHandler.handle(productReservationCommand1);

        /* validate that it is reserved */
        Reservation reservation = reservationRepository.find(random);
        Assert.assertTrue(reservation.isActive());
        Product product = productRepository.find(PRODUCT_ID_ALL);
        Assert.assertTrue(product.isReserved());

        /* same user trying to reserve product */
        UserId user_id_standard = USER_ID_STANDARD;
        AddProductReservationCommand addProductReservationCommand2 = addProductReservationCommand(productId2, random, user_id_standard);
        addProductReservationCommandHandler.handle(addProductReservationCommand2);
    }

    private ProductReservationCommand getProductReservationCommand(List<ProductId> productId, ReservationId random, UserId user_id_premium) {
        ProductReservationCommand productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(productId);
        productReservationCommand.setNewReservationId(random);
        productReservationCommand.setUserId(user_id_premium);
        return productReservationCommand;
    }
    private AddProductReservationCommand addProductReservationCommand(List<ProductId> productId, ReservationId random, UserId user_id_premium) {
        AddProductReservationCommand productReservationCommand = new AddProductReservationCommand();
        productReservationCommand.setProductId(productId);
        productReservationCommand.setReservationId(random);
        productReservationCommand.setUserId(user_id_premium);
        return productReservationCommand;
    }

    @Test(expected = DomainException.class)
    public void shouldReserveProduct3() {
        ProductReservationCommandHandler productReservationCommandHandler = getProductReservationCommandHandler();
        ProductReservationCommand productReservationCommand = new ProductReservationCommand();
        productReservationCommand.setProductId(Collections.singletonList(PRODUCT_ID_PREMIUM));
        productReservationCommand.setUserId(USER_ID_STANDARD);
        ReservationId random = ReservationId.random();
        productReservationCommand.setNewReservationId(random);
        productReservationCommandHandler.handle(productReservationCommand);
    }

    private ProductReservationCommandHandler getProductReservationCommandHandler() {
        return new ProductReservationCommandHandler(
                productRepository
                , reservationRepository
                , productReservationPolicyFactory
                , reservationRuleFactory
        );
    }

    private AddProductReservationCommandHandler addProductReservationCommandHandler() {
        return new AddProductReservationCommandHandler(
                reservationRepository,
                productRepository
                , productReservationPolicyFactory
        );
    }
}
