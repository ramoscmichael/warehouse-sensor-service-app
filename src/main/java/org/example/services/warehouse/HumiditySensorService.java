package org.example.services.warehouse;

import org.example.model.SensorData;
import org.example.model.SensorType;
import org.example.util.PropertyConfig;

import java.util.logging.Logger;

public class HumiditySensorService implements SensorService {

    private static final Logger LOGGER = Logger.getLogger(HumiditySensorService.class.getName());

    @Override
    public SensorType getSensorType() {
        return SensorType.HUMIDITY;
    }

    @Override
    public void performSensorAction(SensorData sensorData) {
        LOGGER.info("Accepted sensor data " + sensorData.toString());
    }

    @Override
    public int getSensorPort() {
        return PropertyConfig.HUMIDITY_SENSOR_PORT;
    }
}
