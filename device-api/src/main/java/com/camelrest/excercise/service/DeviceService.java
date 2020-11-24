package com.camelrest.excercise.service;

import com.camelrest.excercise.dto.Device;
import com.camelrest.excercise.repo.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camelrest.excercise.exception.DeviceAPIException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepo repository;

    public List<Device> getDevices() {
        return repository.findAll();
    }

    public Device addDevice(Device device) {
        return repository.save(device);
    }

    public String removeDevice(Long deviceId) {
        repository.deleteById(deviceId);
        return "Device deleted successfully";
    }

    @GetMapping("/device/{id}")
    public Device getDevice(@PathVariable Long id) throws DeviceAPIException {
        return repository.findById(id).orElseThrow(() -> new DeviceAPIException("Device Id not found"));
    }
}
