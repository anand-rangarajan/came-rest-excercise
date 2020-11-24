package com.camelrest.excercise.controller;

import com.camelrest.excercise.dto.Response;
import com.camelrest.excercise.model.Device;
import com.camelrest.excercise.exception.NetworkAPIException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "network-api" })
@RequestMapping("/network-api")
public interface NetworkController {

    @ApiOperation(value = "Get all devices", response = Response.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "device {deviceName} is removed successfully.", response = Response.class) })
    @GetMapping(value = "/getDevices", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> getDevices() throws NetworkAPIException;

    @ApiOperation(value = "Remove device using Id", response = Response.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "device {deviceName} is removed successfully.", response = Response.class),
                    @ApiResponse(code = 500, message = "device {ID} is missing.", response = Response.class),
                    @ApiResponse(code = 500, message = "device {ID} not found.", response = Response.class)
    })
    @GetMapping(value = "/getDevice/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> getDevice(@PathVariable(required = false) int deviceId) throws NetworkAPIException;

    @ApiOperation(value = "Add new device", response = Device.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "added {deviceName} successfully.", response = Response.class),
                    @ApiResponse(code = 500, message = "deviceName: null found, string expected"),
                    @ApiResponse(code = 500, message = "type: null found, string expected", response = Response.class)
    })
    @PostMapping(path = "/addDevice", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> addDevice(@RequestBody(required = false) Device device) throws NetworkAPIException;

    @ApiOperation(value = "Remove device using Id", response = Response.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "device {deviceName} is removed successfully.", response = Response.class),
                    @ApiResponse(code = 500, message = "device {ID} is missing.", response = Response.class),
                    @ApiResponse(code = 500, message = "device {ID} not found.", response = Response.class)
    })
    @GetMapping(value = "/removeDevice/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> removeDevice(@PathVariable(required = false) int deviceId) throws NetworkAPIException;

}
