/*
 * ParkingServer.java
 *
 * Created on 2019-03-19
 */

package com.valtech.parkinglotrelay.serverapi;

import com.valtech.parkinglotrelay.dto.ParkingSpaceSensorData;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ParkingServer {

    @RequestLine("PUT /sensors/{sensorId}")
    @Headers("Content-Type: application/json")
    void reportStatus(@Param("sensorId") String sensorId, ParkingSpaceSensorData payload);
}
