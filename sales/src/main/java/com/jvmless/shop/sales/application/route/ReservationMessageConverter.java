package com.jvmless.shop.sales.application.route;

import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.application.dto.ReservedProductMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ReservationMessageConverter {

    public static Processor succesfulProductReservationToMessage() {
        return exchange -> {
            ProductReservationCommand productReservationCommand = exchange.getIn().getBody(ProductReservationCommand.class);
            String message = "Product has been reserved";
            String productId = productReservationCommand.getProductId().getId();
            String reservationId = exchange.getIn().getHeader("reservation_id", String.class);
            ReservedProductMessage reservedProductMessage = new ReservedProductMessage(message,productId, reservationId);
            exchange.getOut().setBody(reservedProductMessage);
        };
    }
}
