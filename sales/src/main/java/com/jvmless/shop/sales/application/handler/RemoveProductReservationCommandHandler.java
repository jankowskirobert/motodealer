package com.jvmless.shop.sales.application.handler;

import com.jvmless.shop.sales.domain.product.Product;
import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.sales.domain.product.ProductRepository;
import com.jvmless.shop.sales.domain.reservation.Reservation;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class RemoveProductReservationCommandHandler implements Processor {

    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;

    public RemoveProductReservationCommandHandler(ReservationRepository reservationRepository, ProductRepository productRepository) {
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
    }

    public void handle(RemoveProductReservationCommand removeProductReservationCommand) {
        Reservation reservation = reservationRepository.find(removeProductReservationCommand.getReservationId());
        ProductId productId = removeProductReservationCommand.getProductId();
        Product product = productRepository.find(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        } else {
            if (!product.isReserved()) {
                throw new IllegalStateException("Product is not reserved");
            } else {
                reservation.cancelReservation(productId);
            }
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        RemoveProductReservationCommand removeProductReservationCommand = exchange.getIn().getBody(RemoveProductReservationCommand.class);
        this.handle(removeProductReservationCommand);

    }
}
