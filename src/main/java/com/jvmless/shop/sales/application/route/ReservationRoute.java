package com.jvmless.shop.sales.application.route;

import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.RestConfiguration;

public class ReservationRoute extends RouteBuilder {


    private final ProductReservationCommandHandler productReservationCommandHandler;

    public ReservationRoute(ProductReservationCommandHandler productReservationCommandHandler) {
        this.productReservationCommandHandler = productReservationCommandHandler;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("netty4-http")
        .port("8081")
        .contextPath("/api")
                .bindingMode(RestBindingMode.auto);

        rest("/product")
                .post("/reserve").type(ProductReservationCommand.class).produces("application/json").to("direct:reserve");

        onException(BeanValidationException.class)
                .setBody().constant("Invalid json data")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .handled(true);

        from("direct:reserve")
                .to("bean-validator://x")
                .process(productReservationCommandHandler);

    }
}
