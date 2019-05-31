package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.Product;
import com.jvmless.shop.sales.domain.productcatalog.ProductRepository;
import com.jvmless.shop.sales.domain.productcatalog.ProductReservationPolicyFactory;
import com.jvmless.shop.sales.domain.reservation.*;
import com.jvmless.shop.usermanagement.UserContextService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductReservationCommandHandler {

    private ProductRepository productRepository;
    private UserContextService userContextService;
    private ReservationRepository reservationRepository;
    private ProductReservationPolicyFactory productReservationPolicyFactory;
    private ReservationRuleFactory reservationRuleFactory;

    public void handle(ProductReservationCommand productReservationCommand) {

        Product product = productRepository.find(productReservationCommand.getProductId());
        Reservation reservation = reservationRepository.find(productReservationCommand.getReservationId());
        //can this user reserve anything more?
        reservation.reserve(reservationRuleFactory);

        //if he, then reserve this product for him
        product.reserve(userContextService.getCurrentUserId(), productReservationPolicyFactory);
    }
}
