package com.valtech.parkinglotrelay.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class CreateObjectMapper {

    private CreateObjectMapper() {
        throw new InstantiationError("Go away!");
    }

    public static <T> ObjectMapper forType(Class<T> type) {
        if (ParkingSpaceSensorData.class.equals(type)) {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper;
        }
        throw new IllegalArgumentException("No ObjectMapper for type: " + type == null ? "null" : type.getName());
    }
}
