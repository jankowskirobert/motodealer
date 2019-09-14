package com.jvmless.shop.sales.application.handler;

import com.jvmless.shop.sales.application.command.CancelReservationCommand;
import com.jvmless.shop.sales.domain.product.Product;
import com.jvmless.shop.sales.domain.product.ProductRepository;
import com.jvmless.shop.sales.domain.reservation.Reservation;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CancelReservationCommandHandler implements Processor {

    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;

    public CancelReservationCommandHandler(ReservationRepository reservationRepository, ProductRepository productRepository) {
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
    }

    public void handle(CancelReservationCommand cancelReservationCommand) {
        Reservation reservation = reservationRepository.find(cancelReservationCommand.getReservationId());
        if(reservation.isActive()) {
            reservation.getReservationItems().stream().forEach(reservedItems -> {
                Product reservedProduct = productRepository.find(reservedItems.getProductId());
                reservedProduct.cancelReservation();
            });
            reservation.close();
        } else {
            throw new IllegalArgumentException("Reservation currently closed");
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        CancelReservationCommand cancelReservationCommand = exchange.getIn().getBody(CancelReservationCommand.class);
        handle(cancelReservationCommand);
    }
}
