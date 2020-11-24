package com.camelrest.excercise.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camelrest.excercise.dto.Device;

public interface DeviceRepo extends JpaRepository<Device, Long> {
}
