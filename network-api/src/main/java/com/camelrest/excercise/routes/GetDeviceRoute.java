package com.camelrest.excercise.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GetDeviceRoute extends RouteBuilder {
    @Override
    public void configure() {
        onException(Exception.class).handled(false);
        from("direct:get-device").routeId("get-device-route")
                .setHeader(Exchange.HTTP_METHOD,simple("GET"))
                .setHeader(Exchange.CONTENT_TYPE,simple("application/json"))
                .to("http://localhost:8081/device-api/getDevice");

        from("direct:get-devices").routeId("get-devices-route")
                        .setHeader(Exchange.HTTP_METHOD,simple("GET"))
                        .setHeader(Exchange.CONTENT_TYPE,simple("application/json"))
                        .to("http://localhost:8081/device-api/getDevices");
    }
}
