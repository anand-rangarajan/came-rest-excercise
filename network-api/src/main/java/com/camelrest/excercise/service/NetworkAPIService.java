package com.camelrest.excercise.service;

import com.camelrest.excercise.exception.NetworkAPIException;
import com.networknt.schema.ValidationMessage;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.camelrest.excercise.model.Device;

@Service
public class NetworkAPIService {
    private static final Logger log = LoggerFactory.getLogger(NetworkAPIService.class);

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    public String getDevice(int deviceId) throws NetworkAPIException {
        final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withHeader(Exchange.HTTP_QUERY, "id=" + deviceId).withPattern(ExchangePattern.InOut)
                        .build();
        log.info("Sending exchange message to end point");
        final Exchange responseExchange = producerTemplate.send("direct:get-device", requestExchange);
        log.info("Received response from Exchange");
        return getResponse(responseExchange);
    }

    public String getDevices() throws NetworkAPIException {
        final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withPattern(ExchangePattern.InOut).build();
        log.info("Sending exchange message to end point");
        final Exchange responseExchange = producerTemplate.send("direct:get-devices", requestExchange);
        log.info("Received response from Exchange");
        return getResponse(responseExchange);
    }

    public String processAddDevice(Device device) throws NetworkAPIException {
        final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(device).withPattern(ExchangePattern.InOut).build();
        log.info("Sending exchange message to end point");
        final Exchange responseExchange = producerTemplate.send("direct:add-device", requestExchange);
        log.info("Received response from Exchange");
        return getResponse(responseExchange);
    }

    public String getResponse(final Exchange exchange) throws NetworkAPIException {
        Exception exception = exchange.getException();
        if (ObjectUtils.isEmpty(exception)) {
            return exchange.getMessage().getBody(String.class);
        } else if (exception instanceof JsonValidationException) {

            log.error("Error while validating json request message");
            JsonValidationException validationException = (JsonValidationException) exception;
            log.error(validationException.getErrors().iterator().next().getMessage());
            ValidationMessage validationErrorMessage = validationException.getErrors().iterator().next();

            String errorMessage = validationErrorMessage.getMessage();

            if (errorMessage != null && errorMessage.startsWith("$.") && errorMessage.length() > 2) {
                errorMessage = errorMessage.substring(2);
            } else {
                errorMessage = "Invalid Json Format";
            }
            throw new NetworkAPIException(errorMessage);
        } else {
            log.error("Some other error occured " + exception.getMessage());
            throw new NetworkAPIException(exception.getMessage());
        }
    }

    public String processRemoveDevice(int deviceId) throws NetworkAPIException {
        final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withHeader(Exchange.HTTP_QUERY, "id=" + deviceId).withPattern(ExchangePattern.InOut)
                        .build();
        log.info("Sending exchange message to end point");
        final Exchange responseExchange = producerTemplate.send("direct:remove-device", requestExchange);
        log.info("Received response from Exchange");
        return getResponse(responseExchange);
    }
}
