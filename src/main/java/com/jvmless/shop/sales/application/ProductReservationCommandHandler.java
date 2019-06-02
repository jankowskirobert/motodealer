package com.jvmless.shop.sales.application;

import com.jvmless.shop.sales.domain.productcatalog.Product;
import com.jvmless.shop.sales.domain.productcatalog.ProductId;
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

    public ReservationId handle(ProductReservationCommand productReservationCommand) {
        UserId currentUserId = userContextService.getCurrentUserId();
        if (currentUserId == null)
            throw new IllegalStateException("User not found!");
        ReservationId reservationId = productReservationCommand.getReservationId();
        Reservation reservation = null;
        if (reservationId == null) {
            ReservationId newReservationId = ReservationId.random();
            Reservation exists = reservationRepository.find(newReservationId);
            if (exists == null) {
                reservation = new Reservation(newReservationId, currentUserId);
                reservationId = newReservationId;
            }
        } else {
            reservation = reservationRepository.find(reservationId);
            if (reservation == null) {
                throw new IllegalArgumentException("Reservation not found!");
            }
        }
        ProductId productId = productReservationCommand.getProductId();
        reserveProduct(productId, reservation);
        Product product = productRepository.find(productId);
        if (product == null)
            throw new IllegalArgumentException("Product does not exist!");
        product.reserve(currentUserId, productReservationPolicyFactory);
        return reservationId;
    }

    private void reserveProduct(ProductId productId, Reservation reservation) {
        if (reservation.isActive())
            reservation.reserve(productId, reservationRuleFactory);
        else
            throw new IllegalStateException("Reservation is already closed");
    }


}
