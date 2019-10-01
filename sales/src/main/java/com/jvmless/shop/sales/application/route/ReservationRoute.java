package com.jvmless.shop.sales.application.route;

import com.jvmless.shop.sales.application.command.AddProductReservationCommand;
import com.jvmless.shop.sales.application.command.ProductReservationCommand;
import com.jvmless.shop.sales.application.handler.AddProductReservationCommandHandler;
import com.jvmless.shop.sales.application.handler.ProductReservationCommandHandler;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

public class ReservationRoute extends RouteBuilder {


    private final ProductReservationCommandHandler productReservationCommandHandler;
    private final AddProductReservationCommandHandler addProductReservationCommandHandler;

    public ReservationRoute(ProductReservationCommandHandler productReservationCommandHandler, AddProductReservationCommandHandler addProductReservationCommandHandler) {
        this.productReservationCommandHandler = productReservationCommandHandler;
        this.addProductReservationCommandHandler = addProductReservationCommandHandler;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("netty4-http")
                .port("8081")
//                .contextPath("/api")
                .bindingMode(RestBindingMode.auto);

        rest("/product")
                .post("/reserve").type(ProductReservationCommand.class).produces("application/json").to("direct:reserve")
                .post("/reserve/add").type(AddProductReservationCommand.class).produces("application/json").to("direct:addToReserve");

        onException(BeanValidationException.class)
                .setBody().constant("Invalid json data")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .handled(true);

        from("direct:reserve")
                .to("bean-validator://x")
                .process(productReservationCommandHandler)
                .process(ReservationMessageConverter.succesfulProductReservationToMessage())
                .marshal().json(JsonLibrary.Jackson)
                .to("{{integration.route.product.reserved}}");

        from("direct:addToReserve")
                .to("bean-validator://x")
                .process(addProductReservationCommandHandler)
                .process(ReservationMessageConverter.succesfulAddProductReservationToMessage())
                .marshal().json(JsonLibrary.Jackson)
                .to("{{integration.route.product.reserved}}");


    }
}
