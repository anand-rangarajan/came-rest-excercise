package com.camelrest.excercise.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ValidatorRoutes extends RouteBuilder {
    @Override
    public void configure() {

        validator().type("json:network-api").withUri("json-validator:{{network-api-schema.json}}");
        validator().type("json:device-service-api").withUri("json-validator:{{device-schema.json}}");

        from("direct:validate-network-api")
                .routeId("validate-network-api-route")
                .log("network api message  Received: ${body}")
                .inputTypeWithValidate("json:network-api")
                .log("Network API JSON  Validation: SUCCESS");

        from("direct:validate-device-service-api")
                .routeId("device-service-api-route")
                .log("Device Service JSON  Received: ${body}")
                .inputTypeWithValidate("json:device-service-api")
                .log("Device Service JSON  Validation: SUCCESS");
    }
}
