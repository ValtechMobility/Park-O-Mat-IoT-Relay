package com.valtech.parkinglotrelay.serverapi;/*
 * ParkingServer
 *
 * Created on 07.06.18
 *
 * Copyright (C) 2018 Volkswagen AG, All rights reserved.
 */

import com.valtech.parkinglotrelay.dto.ParkingSpaceSensorData;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ParkingServer {

    @RequestLine("PUT /sensors/{sensorId}")
    @Headers("Content-Type: application/json")
    void reportStatus(@Param("sensorId") String sensorId, ParkingSpaceSensorData payload);
}
