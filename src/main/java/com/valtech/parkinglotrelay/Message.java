package com.valtech.parkinglotrelay;
/*
 * Message
 *
 * Created on 07.06.18
 *
 * Copyright (C) 2018 Volkswagen AG, All rights reserved.
 */

import lombok.Data;

@Data
public class Message {
    private boolean realtime;
    private boolean occupied;
    private String sensorId;
    private Integer voltage;
    private Integer signalStrength;
}
