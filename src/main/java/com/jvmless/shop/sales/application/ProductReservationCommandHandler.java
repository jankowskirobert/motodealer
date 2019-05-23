package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.reservation.Product;
import com.jvmless.shop.sales.domain.reservation.Reservation;

public class ProductReservationCommandHandler {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private UserContextService userContextService;
    private ReservationRepository reservationRepository;


    public void handle(ProductReservationCommand productReservationCommand) {
        Product product = productRepository.find(productReservationCommand.getProductId());
        Reservation reservation = reservationRepository.find(productReservationCommand.getReservationId());


        product.reserve(userContextService.getCurrentUserId());
    }
}
