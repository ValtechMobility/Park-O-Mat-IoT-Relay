/*
 * MessageFactory.java
 *
 * Created on 2019-03-19
 */

package com.valtech.parkinglotrelay;

import java.util.Arrays;

public class MessageFactory {

    private static final int REALTIME = 0x01;
    private static final int TYPE_OFFSET = 2;
    private static final int OCCUPATION_OFFSET = 5;
    private static final int OCCUPIED = 0x01;
    private static final int SENSOR_ID_OFFSET = 10;
    private static final int ID_LENGTH = 4;
    private static final int VOLTAGE_OFFSET = 14;
    private static final int SIGNAL_STRENGTH_OFFSET = 15;

    public static Message fromBytes(final Byte[] bytes) {
        final Message message = new Message();
        if (bytes[TYPE_OFFSET].intValue() == REALTIME) {
            message.setRealtime(true);
        } else {
            message.setRealtime(false);
        }
        if (bytes[OCCUPATION_OFFSET].intValue() == OCCUPIED) {
            message.setOccupied(true);
        } else {
            message.setOccupied(false);
        }
        final Byte[] serialBytes = Arrays.copyOfRange(bytes, SENSOR_ID_OFFSET, SENSOR_ID_OFFSET + ID_LENGTH);
        final StringBuilder sensorId = new StringBuilder();
        for (final Byte serialByte : serialBytes) {
            sensorId.insert(0, String.format("%02X", serialByte));
        }
        message.setSensorId(sensorId.toString());
        if (!message.isRealtime()) {
            message.setVoltage(bytes[VOLTAGE_OFFSET].intValue() * 100);
            message.setSignalStrength(bytes[SIGNAL_STRENGTH_OFFSET].intValue() - 30);
        }
        return message;
    }
}
