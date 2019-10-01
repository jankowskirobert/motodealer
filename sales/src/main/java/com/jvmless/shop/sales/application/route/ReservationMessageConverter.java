package com.jvmless.shop.sales.application.route;

import com.jvmless.shop.sales.application.command.AddProductReservationCommand;
import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.application.dto.ReservedProductMessage;
import com.jvmless.shop.sales.domain.product.ProductId;
import com.jvmless.shop.sales.domain.reservation.ReservationId;
import org.apache.camel.Processor;

import java.util.List;

public class ReservationMessageConverter {

    public static Processor succesfulProductReservationToMessage() {
        return exchange -> {
            ProductReservationCommand productReservationCommand = exchange.getIn().getBody(ProductReservationCommand.class);
            String message = "Product has been reserved";
            List<ProductId> productId = productReservationCommand.getProductId();
            ReservationId reservationId = productReservationCommand.getNewReservationId();
            ReservedProductMessage reservedProductMessage = new ReservedProductMessage(message, productId, reservationId);
            exchange.getOut().setBody(reservedProductMessage);
        };
    }
    public static Processor succesfulAddProductReservationToMessage() {
        return exchange -> {
            AddProductReservationCommand productReservationCommand = exchange.getIn().getBody(AddProductReservationCommand.class);
            String message = "Product has been reserved";
            List<ProductId> productId = productReservationCommand.getProductId();
            ReservationId reservationId = productReservationCommand.getReservationId();
            ReservedProductMessage reservedProductMessage = new ReservedProductMessage(message, productId, reservationId);
            exchange.getOut().setBody(reservedProductMessage);
        };
    }
}
