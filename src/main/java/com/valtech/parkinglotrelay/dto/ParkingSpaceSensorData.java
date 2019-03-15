/*
 * ParkingSpaceSensorData.java
 *
 * Created on 2019-03-19
 */

package com.valtech.parkinglotrelay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
