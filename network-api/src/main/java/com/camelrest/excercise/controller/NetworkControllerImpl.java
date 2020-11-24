package com.camelrest.excercise.controller;

import com.camelrest.excercise.dto.Response;
import com.camelrest.excercise.dto.ReplyStatus;
import com.camelrest.excercise.model.Device;
import com.camelrest.excercise.service.NetworkAPIService;
import com.camelrest.excercise.exception.NetworkAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetworkControllerImpl implements NetworkController {

    private static final Logger log = LoggerFactory.getLogger(NetworkControllerImpl.class);

    @Autowired
    NetworkAPIService service;

    @Override
    public ResponseEntity<Response> addDevice(Device device) throws NetworkAPIException {
        log.info("Add device... ");
        final String response = service.processAddDevice(device);
        return ResponseEntity.created(null).body(new Response(ReplyStatus.SUCCESS, response));
    }

    @Override
    public ResponseEntity<Response> removeDevice(int deviceId) throws NetworkAPIException {
        log.info("Remove device... ");
        final String response = service.processRemoveDevice(deviceId);
        return ResponseEntity.ok(new Response(ReplyStatus.SUCCESS, response));
    }

    @Override public ResponseEntity<Response> getDevices() throws NetworkAPIException {
        log.info("Get devices... ");
        final String response = service.getDevices();
        return ResponseEntity.ok(new Response(ReplyStatus.SUCCESS, response));
    }

    @Override
    public ResponseEntity<Response> getDevice(int deviceId) throws NetworkAPIException {
        log.info("Get device... ");
        final String response = service.getDevice(deviceId);
        return ResponseEntity.ok(new Response(ReplyStatus.SUCCESS, response));
    }
}
