/*
 * ParkingLotTest.java
 *
 * Created on 2019-03-19
 */

package com.valtech.parkinglotrelay.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingLotTest {

    private final ObjectMapper mapper;

    public ParkingLotTest() {
        mapper = CreateObjectMapper.forType(ParkingSpaceSensorData.class);
    }

    @Test
    void theParkingLotIsSerializableToJson() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (final OutputStream out = byteArrayOutputStream) {
            mapper.writer().writeValue(out, new ParkingSpaceSensorData("42", Instant.now(), false, 3700, -98));
        }

        final String jsonString = byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
        assertThat(jsonString).contains("\"batteryVoltage\":3700");
        assertThat(jsonString).contains("\"sensorId\":\"42\"");
        assertThat(jsonString).contains("\"occupied\":false");
        assertThat(jsonString).contains("\"rssi\":-98");
    }

    @Test
    void theParkingLotCanBeDeserialized() throws IOException {
        final String parkingLotJson = "{\"sensorId\":\"42\",\"timestamp\":1528372244.175000000,"
                + "\"occupied\":false, \"batteryVoltage\":3700, \"rssi\":-98}";

        final ParkingSpaceSensorData parkingLot = mapper.reader().forType(ParkingSpaceSensorData.class).readValue(parkingLotJson);

        assertThat(parkingLot).isNotNull();
        assertThat(parkingLot.getSensorId()).isEqualTo("42");
        assertThat(parkingLot.getTimestamp()).isNotNull();
        assertThat(parkingLot.isOccupied()).isFalse();
        assertThat(parkingLot.getBatteryVoltage()).isEqualTo(3700);
        assertThat(parkingLot.getRssi()).isEqualTo(-98);
    }
}
