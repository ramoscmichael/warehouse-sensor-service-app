package org.example.services;

import org.example.services.warehouse.SensorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WarehouseServices {

    private static final Logger LOGGER = Logger.getLogger(WarehouseServices.class.getName());

    private final List<SensorService> sensorServiceList = new ArrayList<>();
    private final List<UDPSensorService> udpSensorServices = new ArrayList<>();
    private final ExecutorService sensorExecutorService = Executors.newFixedThreadPool(5);


    private static class WarehouseServiceInstance {
        private static final WarehouseServices INSTANCE = new WarehouseServices();
    }

    public static WarehouseServices getInstance() {
        return WarehouseServiceInstance.INSTANCE;
    }

    public WarehouseServices addService(SensorService sensorService) {
        this.sensorServiceList.add(sensorService);
        return this;
    }

    /**
     * Start all registered SensorService as UDP server
     */
    public void start() throws Exception {
        this.udpSensorServices.addAll(this.sensorServiceList.stream().map(UDPSensorService::new).collect(Collectors.toList()));
        this.udpSensorServices.forEach(udp -> this.sensorExecutorService.submit(() -> this.startUDPServer(udp)) );
    }

    private void startUDPServer(UDPSensorService server) {
        try {
            server.init();
            LOGGER.info( "Started UDP server " + server.toString());
            server.start();
        } catch (Exception ex) {
            LOGGER.severe( "Error in starting UDP server " + server.toString() + ": " + ex.getMessage());
        }
    }

    public void stop() {
        this.sensorExecutorService.shutdown();
        this.udpSensorServices.clear();
    }

}
