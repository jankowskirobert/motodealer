package com.jvmless.shop.sales.application.handler;

import com.jvmless.shop.sales.application.command.AddProductReservationCommand;
import com.jvmless.shop.sales.domain.product.Product;
import com.jvmless.shop.sales.domain.product.ProductRepository;
import com.jvmless.shop.sales.domain.product.ProductReservationPolicy;
import com.jvmless.shop.sales.domain.product.ProductReservationPolicyFactory;
import com.jvmless.shop.sales.domain.reservation.Reservation;
import com.jvmless.shop.sales.domain.reservation.ReservationRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AddProductReservationCommandHandler implements Processor {
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final ProductReservationPolicyFactory productReservationPolicyFactory;

    public AddProductReservationCommandHandler(ReservationRepository reservationRepository, ProductRepository productRepository, ProductReservationPolicyFactory productReservationPolicyFactory) {
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;
        this.productReservationPolicyFactory = productReservationPolicyFactory;
    }


    public void handle(AddProductReservationCommand addProductReservationCommand) {
        Reservation reservation = reservationRepository.find(addProductReservationCommand.getReservationId());
        if (reservation.isActive() && reservation.getUserId().equals(addProductReservationCommand.getUserId())) {
            addProductReservationCommand.getProductId().stream().forEach(
                    productId -> {
                        if (reservation.contains(productId)) {
                            throw new IllegalStateException("User already reserved this item");
                        } else {
                            Product product = productRepository.find(productId);
                            if (product == null) {
                                throw new IllegalArgumentException("Product with given id, not exists: " + productId.getId());
                            } else {
                                if (product.isAvailable()) {
                                    ProductReservationPolicy productReservationPolicy = productReservationPolicyFactory.generate(
                                            product.reservationPolicyType(),
                                            addProductReservationCommand.getUserId()
                                    );
                                    product.reserve(addProductReservationCommand.getUserId(), productReservationPolicy);
                                } else {
                                    throw new IllegalArgumentException("Product is currently reserved: " + productId.getId());
                                }
                            }
                        }
                    }
            );
        } else {
            throw new IllegalArgumentException("Reservation is unreachable or not belongs to given user");
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        AddProductReservationCommand addProductReservationCommand = exchange.getIn().getBody(AddProductReservationCommand.class);
        this.handle(addProductReservationCommand);
    }
}
