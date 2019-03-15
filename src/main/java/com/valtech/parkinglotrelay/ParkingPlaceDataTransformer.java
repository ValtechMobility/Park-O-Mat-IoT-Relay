/*
 * ParkingPlaceDataTransformer.java
 *
 * Created on 2019-03-19
 */

package com.valtech.parkinglotrelay;

import com.valtech.parkinglotrelay.dto.CreateObjectMapper;
import com.valtech.parkinglotrelay.dto.ParkingSpaceSensorData;
import com.valtech.parkinglotrelay.serverapi.ParkingServer;
import feign.Feign;
import feign.FeignException;
import feign.RetryableException;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.stream.IntStream;

@Slf4j
public class ParkingPlaceDataTransformer {

    public static void main(final String[] args) throws IOException {
        final String serverUrl = System.getProperty("parking.BackendUrl", "needsToBeAProperty");
        final int listenPort = Integer.parseInt(System.getProperty("parking.listenPort", "6000"));
        final byte[] input = new byte[20];

        log.debug("Interpreted data will be pushed to backend (serverURL): " + serverUrl);

        try (final ServerSocket serverSocket = new ServerSocket(listenPort);
             final Socket accept = serverSocket.accept();
             final InputStream inputStream = accept.getInputStream()) {
            log.info("Socket opened, listening " + listenPort);

            while (Boolean.TRUE) {
                final int numberOfBytes = inputStream.read(input, 0, input.length);
                maybeLogRawData(input, numberOfBytes);
                final Byte[] inputBoxed = new Byte[numberOfBytes];
                IntStream.range(0, numberOfBytes - 1).forEach(i -> inputBoxed[i] = input[i]);
                final Message message;
                try {
                    message = MessageFactory.fromBytes(inputBoxed);
                } catch (ArrayIndexOutOfBoundsException ioobex) {
                    log.error("Message broken.", ioobex);
                    continue;
                }

                log.debug("Received: {}", message);
                final ParkingServer parkingServer = Feign.builder()
                        .encoder(new JacksonEncoder(CreateObjectMapper.forType(
                                ParkingSpaceSensorData.class)))
                        .target(ParkingServer.class, serverUrl);
                try {
                    final ParkingSpaceSensorData parkingLot =
                            new ParkingSpaceSensorData(message.getSensorId(), Instant.now(), message.isOccupied(),
                                    message.getVoltage(), message.getSignalStrength());
                    parkingServer.reportStatus(message.getSensorId(), parkingLot);
                } catch (RetryableException e) {
                    log.error("Unexpected exception", e);
                } catch (FeignException e) {
                    log.error("Really unexpected exception", e);
                }
            }
        } catch (NullPointerException e) {
            log.error("Stream closed or died unexpectedly. To continue, restart relay! ", e);
        }
    }

    private static void maybeLogRawData(final byte[] input, final int numberOfBytes) {
        if (log.isDebugEnabled()) {
            final StringBuilder rawData = new StringBuilder();
            for (int i = 0; i < numberOfBytes; i++) {
                rawData.append(String.format("%02X ", input[i]));
            }
            log.debug(rawData.toString());
        }
    }
}
