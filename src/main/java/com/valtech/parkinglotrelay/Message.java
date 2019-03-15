/*
 * Message.java
 *
 * Created on 2019-03-19
 */

package com.valtech.parkinglotrelay;

import lombok.Data;

@Data
public class Message {
    private boolean realtime;
    private boolean occupied;
    private String sensorId;
    private Integer voltage;
    private Integer signalStrength;
}
