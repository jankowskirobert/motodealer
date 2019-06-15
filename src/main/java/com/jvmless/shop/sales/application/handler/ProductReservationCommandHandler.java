package com.jvmless.shop.sales.application.handler;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.domain.productcatalog.*;
import com.jvmless.shop.sales.domain.reservation.*;
import com.jvmless.shop.usermanagement.UserContextService;
import com.jvmless.shop.usermanagement.UserId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
                reservationRepository.save(reservation);
                reservationId = newReservationId;
            }
        } else {
            reservation = reservationRepository.find(reservationId);
            if (reservation == null) {
                throw new IllegalArgumentException("Reservation not found!");
            }
        }
        try {
            ReservationPolicy reservationPolicy = reservationRuleFactory.generate(
                    reservation.getReservationRule(),
                    reservation.getReservationItems(),
                    currentUserId
            );
            ProductId productId = productReservationCommand.getProductId();
            reservation.reserve(productId, reservationPolicy);
            Product product = productRepository.find(productId);
            if (product == null)
                throw new IllegalArgumentException("Product does not exist!");
            ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(
                    product.reservationPolicyType(),
                    currentUserId
            );
            product.reserve(currentUserId, productReservationPolicy);
        } catch (DomainException ex) {
            log.error("Could not reserve product");
            throw ex;
        }
        return reservationId;
    }

}
