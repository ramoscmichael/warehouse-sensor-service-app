package org.example;

import org.example.services.CentralService;
import org.example.services.central.HumiditySensorThresholdMonitor;
import org.example.services.central.TemperatureSensorThresholdMonitor;

public class CentralServiceApp
{
    public void start() throws Exception{
        CentralService.getInstance()
                .addSensorThresholdMonitor(new HumiditySensorThresholdMonitor())
                .addSensorThresholdMonitor(new TemperatureSensorThresholdMonitor())
                .start();
    }

    public static void main( String[] args ) throws Exception {
        new CentralServiceApp().start();
    }
}
