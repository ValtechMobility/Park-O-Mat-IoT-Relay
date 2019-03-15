package com.valtech.parkinglotrelay.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParkingSpaceSensorData {

    private String sensorId;

    private Instant timestamp;

    private boolean occupied;

    private Integer batteryVoltage;

    private Integer rssi;

}
