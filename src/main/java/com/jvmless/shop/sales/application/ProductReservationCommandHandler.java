package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.reservation.*;
import com.jvmless.shop.usermanagement.UserRepository;

public class ProductReservationCommandHandler {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private UserContextService userContextService;
    private ReservationRepository reservationRepository;
    private ProductReservationPolicyFactory productReservationPolicyFactory;

    public void handle(ProductReservationCommand productReservationCommand) {

        Product product = productRepository.find(productReservationCommand.getProductId());
        Reservation reservation = reservationRepository.find(productReservationCommand.getReservationId());

        MaxReservationsPolicy maxReservationsPolicy = new MaxReservationsPolicy(userRepository);
        //can this user reserve anything more?
        reservation.reserve(maxReservationsPolicy);

        OnlyOneReservationByUserPolicy onlyOneReservationByUserPolicy = new OnlyOneReservationByUserPolicy();
        //if he, then reserve this product for him
        product.reserve(userContextService.getCurrentUserId(), productReservationPolicyFactory);
    }
}
