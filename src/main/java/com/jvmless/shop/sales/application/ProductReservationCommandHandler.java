package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.reservation.MaxReservationsPolicy;
import com.jvmless.shop.sales.domain.reservation.OnlyOneReservationByUserPolicy;
import com.jvmless.shop.sales.domain.reservation.Product;
import com.jvmless.shop.sales.domain.reservation.Reservation;
import com.jvmless.shop.usermanagement.UserRepository;

public class ProductReservationCommandHandler {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private UserContextService userContextService;
    private ReservationRepository reservationRepository;


    public void handle(ProductReservationCommand productReservationCommand) {
        Product product = productRepository.find(productReservationCommand.getProductId());
        Reservation reservation = reservationRepository.find(productReservationCommand.getReservationId());

        MaxReservationsPolicy maxReservationsPolicy = new MaxReservationsPolicy(userRepository);
        //can this user reserve anything more?
        reservation.reserve(maxReservationsPolicy);

        OnlyOneReservationByUserPolicy onlyOneReservationByUserPolicy = new OnlyOneReservationByUserPolicy();
        //if he, then reserve this product for him
        product.reserve(userContextService.getCurrentUserId(), onlyOneReservationByUserPolicy);
    }
}
