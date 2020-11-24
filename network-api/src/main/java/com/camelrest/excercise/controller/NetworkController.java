package com.camelrest.excercise.controller;

import com.camelrest.excercise.dto.Response;
import com.camelrest.excercise.model.Device;
import com.camelrest.excercise.exception.NetworkAPIException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/network-api")
public interface NetworkController {

    @GetMapping(value = "/getDevices", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> getDevices() throws NetworkAPIException;

    @GetMapping(value = "/getDevice/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> getDevice(@PathVariable(required = false) int deviceId) throws NetworkAPIException;

    @PostMapping(path = "/addDevice", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> addDevice(@RequestBody(required = false) Device device) throws NetworkAPIException;

    @GetMapping(value = "/removeDevice/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> removeDevice(@PathVariable(required = false) int deviceId) throws NetworkAPIException;

}
