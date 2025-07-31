package org.example;

import org.example.services.WarehouseServices;
import org.example.services.warehouse.HumiditySensorService;
import org.example.services.warehouse.TemperatureSensorService;

public class WarehouseServicesApp {

    public void start() throws Exception {
        WarehouseServices.getInstance()
                .addService(new HumiditySensorService())
                .addService(new TemperatureSensorService())
                .start();
    }

    public static void main( String[] args ) throws Exception {
        new WarehouseServicesApp().start();
    }
}
