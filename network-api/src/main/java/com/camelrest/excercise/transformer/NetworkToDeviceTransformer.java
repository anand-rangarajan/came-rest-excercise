package com.camelrest.excercise.transformer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class NetworkToDeviceTransformer extends RouteBuilder {

    @Override
    public void configure() {

        //Transformed to device
        from("direct:transform-to-device")
                        .routeId("transform-to-device-route")
                        .inputType("json:device-service-api")
                        .log("Device API JSON  Transformed: ${body}");

        //Transform network api to Device
        from("direct:network-api-to-device")
                        .routeId("network-api-to-device-route")
                        .inputType("json:network-api")
                        .log("Network API JSON  Received: ${body}")
                        .to("direct:transform-to-device");

        transformer().fromType("json:network-api")
                        .toType("json:device-service-api")
                        .withUri("jolt:{{network-to-device-transformation.path}}?inputType=JsonString&outputType=JsonString&contentCache=true");
    }
}
