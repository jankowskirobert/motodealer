package com.jvmless.shop.sales.application.handler;

import com.jvmless.shop.core.DomainException;
import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.domain.product.*;
import com.jvmless.shop.sales.domain.reservation.*;
import com.jvmless.shop.usermanagement.UserId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class ProductReservationCommandHandler implements Processor {

    private ProductRepository productRepository;
    private ReservationRepository reservationRepository;
    private ProductReservationPolicyFactory productReservationPolicyFactory;
    private ReservationRuleFactory reservationRuleFactory;

    /*
    TODO: klient moze miec tylko jedna otwarta rezerwacje w systemie!! brakuje tej opcji tutaj zeby wyszukiwac rezerwacji po userid
     */
    public void handle(ProductReservationCommand productReservationCommand) {
        UserId currentUserId = productReservationCommand.getUserId();
        ReservationId reservationId = productReservationCommand.getNewReservationId();
        List<Reservation> reservations = reservationRepository.findByUserId(currentUserId);
        long count = reservations.stream().filter(Reservation::isActive).count();

        if(reservationId == null) {
            throw new IllegalArgumentException("Empty or null reservationId!");
        }

        if(currentUserId == null) {
            throw new IllegalArgumentException("Empty or null userId!");
        }

        if (count < 1) {
            Reservation reservation = new Reservation(reservationId, currentUserId);
            ReservationPolicy reservationPolicy = reservationRuleFactory.generate(
                    reservation.getReservationRule(),
                    reservation.getReservationItems(),
                    currentUserId
            );
            productReservationCommand.getProductId().stream().forEach(productId -> {
                Product product = productRepository.find(productId);
                if (product == null)
                    throw new IllegalArgumentException("Product does not exist!");
                ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(
                        product.reservationPolicyType(),
                        currentUserId
                );
                product.reserve(currentUserId, productReservationPolicy);
                reservation.reserve(productId, reservationPolicy);
            });
            reservationRepository.save(reservation);
        } else {
            throw new IllegalStateException("Cannot create new reservation, user has one still active");
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        ProductReservationCommand productReservationCommand = exchange.getIn().getBody(ProductReservationCommand.class);
        this.handle(productReservationCommand);
    }
}
