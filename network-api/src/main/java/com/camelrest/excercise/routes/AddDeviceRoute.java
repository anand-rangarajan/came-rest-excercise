package com.camelrest.excercise.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;
import com.camelrest.excercise.model.Device;

@Component
public class AddDeviceRoute extends RouteBuilder {

    JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Device.class);
    @Override
    public void configure() {
        onException(Exception.class).handled(false);
        from("direct:add-device").routeId("add-device-route")
                .setHeader(Exchange.HTTP_METHOD,simple("POST"))
                .setHeader(Exchange.CONTENT_TYPE,simple("application/json"))
                .marshal(jsonDataFormat)
                .to("direct:validate-network-api")
                .to("direct:network-api-to-device")
                .to("direct:validate-device-service-api")
                .to("http://localhost:8081/device-api/addDevice");
    }
}
