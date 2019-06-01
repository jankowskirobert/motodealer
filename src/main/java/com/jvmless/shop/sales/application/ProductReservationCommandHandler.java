package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.Product;
import com.jvmless.shop.sales.domain.productcatalog.ProductRepository;
import com.jvmless.shop.sales.domain.productcatalog.ProductReservationPolicyFactory;
import com.jvmless.shop.sales.domain.reservation.*;
import com.jvmless.shop.usermanagement.UserContextService;
import com.jvmless.shop.usermanagement.UserId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductReservationCommandHandler {

    private ProductRepository productRepository;
    private UserContextService userContextService;
    private ReservationRepository reservationRepository;
    private ProductReservationPolicyFactory productReservationPolicyFactory;
    private ReservationRuleFactory reservationRuleFactory;

    public void handle(ProductReservationCommand productReservationCommand) {
        UserId currentUserId = userContextService.getCurrentUserId();
        if(currentUserId == null)
            throw new IllegalStateException("User is not exists!");
        Product product = productRepository.find(productReservationCommand.getProductId());
        if(product == null)
            throw new IllegalArgumentException("Product does not exist!");
        Reservation reservation = reservationRepository.find(productReservationCommand.getReservationId());
        if (reservation == null) {
            reservation = new Reservation(ReservationId.random(), currentUserId);
        }
        reservation.reserve(reservationRuleFactory);

        product.reserve(currentUserId, productReservationPolicyFactory);

    }
}
