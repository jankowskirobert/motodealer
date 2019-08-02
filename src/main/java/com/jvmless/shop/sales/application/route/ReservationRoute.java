package com.jvmless.shop.sales.application.route;

import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class ReservationRoute extends RouteBuilder {


    private final ProductReservationCommandHandler productReservationCommandHandler;

    public ReservationRoute(ProductReservationCommandHandler productReservationCommandHandler) {
        this.productReservationCommandHandler = productReservationCommandHandler;
    }

    @Override
    public void configure() throws Exception {

        from("restlet:{{base.url}}/reserve?restletMethod=post")
                .unmarshal()
                .json(JsonLibrary.Jackson)
                .process(productReservationCommandHandler);
    }
}
