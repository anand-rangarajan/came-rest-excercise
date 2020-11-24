package com.camelrest.excercise.transformer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DeviceToNetworkTransformer extends RouteBuilder {
    @Override
    public void configure() {
        transformer().fromType("json:device-service-api")
                .toType("json:network-api")
                .withUri("jolt:{{device-to-network-transformation.path}}?inputType=JsonString&outputType=JsonString&contentCache=true");

        //Transform Device to Network api
        from("direct:device-to-network-api")
                .routeId("device-to-network-api-route")
                .inputType("json:device-service-api")
                .log("Device JSON Received: ${body}")
                .to("direct:transform-to-network-api");

        //Transformed to network-api
        from("direct:transform-to-network-api")
                .routeId("transform-to-network-api-route")
                .inputType("json:network-api")
                .log("Network JSON  Transformed: ${body}");

    }
}
