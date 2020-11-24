package com.camelrest.excercise.controller;

import com.camelrest.excercise.dto.Device;
import com.camelrest.excercise.exception.DeviceAPIException;
import com.camelrest.excercise.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/device-api")
public class DeviceController {
    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    DeviceService service;

    @PostMapping(path = "/addDevice", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addDevice(@RequestBody Device device) {
        log.info("Device " + device);
        Device newDevice = service.addDevice(device);
        return "Added " + newDevice.getName() + " successfully.";
    }

    @GetMapping(path = "/removeDevice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeDevice(@RequestParam(name = "id") Long deviceId) {
        log.info("DeviceId " + deviceId);

        String response = service.removeDevice(deviceId);
        return ResponseEntity.created(null).body(response);
    }

    @GetMapping(path = "/getDevices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Device>> getDevices() {
        List<Device> deviceList = service.getDevices();
        return ResponseEntity.created(null).body(deviceList);
    }

    @GetMapping(path = "/getDevice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> getDevice(@RequestParam(name = "id")  Long deviceId) throws DeviceAPIException {
        Device device = service.getDevice(deviceId);
        return ResponseEntity.created(null).contentType(MediaType.APPLICATION_JSON).body(device);
    }
}
