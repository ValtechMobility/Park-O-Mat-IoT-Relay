package com.valtech.parkinglotrelay;
/*
 * MessageFactoryTest
 *
 * Created on 07.06.18
 *
 * Copyright (C) 2018 Volkswagen AG, All rights reserved.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.common.io.BaseEncoding;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageFactoryTest {
    @Test
    public void recognizesRealtimePacket() {
        final Message message =
                MessageFactory.fromBytes(stringToBytes("EF 00 01 DC 0D 01 31 D2 1A 24 71 D3 1A 24 FB C2 8E 49 6E"));
        assertThat(message.isRealtime()).isTrue();
    }

    @Test
    public void recognizesHeartbeatPacket() {
        final Message message =
                MessageFactory.fromBytes(stringToBytes("EF 00 00 CB 0C 00 31 D2 1A 24 71 D4 1A 24 25 BC 12 0A"));
        assertThat(message.isRealtime()).isFalse();
    }

    @Test
    public void recognizesOccupiedSpace() {
        final Message message =
                MessageFactory.fromBytes(stringToBytes("EF 00 01 DC 0D 01 31 D2 1A 24 71 D3 1A 24 FB C2 8E 49 6E"));
        assertThat(message.isOccupied()).isTrue();
    }

    @Test
    public void recognizesFreeSpace() {
        final Message message =
                MessageFactory.fromBytes(stringToBytes("EF 00 01 DC 0D 00 31 D2 1A 24 71 D3 1A 24 FB C2 8E 49 6E"));
        assertThat(message.isOccupied()).isFalse();
    }

    @Test
    public void correctlyParsesSensorId() {
        final Message message =
                MessageFactory.fromBytes(stringToBytes("EF 00 01 DC 0D 01 31 D2 1A 24 71 D3 1A 24 FB C2 8E 49 6E"));
        assertThat(message.getSensorId()).isEqualTo("241AD371");
    }

    @Test
    public void correctlyParsesVoltage() {
        final Message message =
                MessageFactory.fromBytes(stringToBytes("EF 00 00 CB 0C 00 31 D2 1A 24 71 D4 1A 24 25 BC 12 0A"));
        assertThat(message.getVoltage()).isEqualTo(3700);
    }

    @Test
    public void correctlyParsesSignalStrength() {
        final Message message =
                MessageFactory.fromBytes(stringToBytes("EF 00 00 CB 0C 00 31 D2 1A 24 71 D4 1A 24 25 BC 12 0A"));
        assertThat(message.getSignalStrength()).isEqualTo(-98);
    }

    private Byte[] stringToBytes(final String byteString) {
        final String[] characters = byteString.split(" ");
        final List<Byte> bytes = new ArrayList<>();
        for (final String byteChar : characters) {
            final byte[] decoded = BaseEncoding.base16().decode(byteChar);
            bytes.add(decoded[0]);
        }
        return bytes.toArray(new Byte[0]);
    }
}
