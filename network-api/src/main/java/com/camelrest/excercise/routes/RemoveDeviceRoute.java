package com.camelrest.excercise.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RemoveDeviceRoute extends RouteBuilder {
    @Override
    public void configure() {
        onException(Exception.class).handled(false);
        from("direct:remove-device").routeId("remove-device-route")
                .setHeader(Exchange.HTTP_METHOD,simple("GET"))
                .setHeader(Exchange.CONTENT_TYPE,simple("application/json"))
                .to("http://localhost:8081/device-api/removeDevice");
    }
}
