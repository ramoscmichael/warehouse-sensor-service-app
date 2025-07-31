package org.example.services.warehouse;

import org.example.model.SensorData;
import org.example.model.SensorType;
import org.example.util.PropertyConfig;

import java.util.logging.Logger;

public class TemperatureSensorService implements SensorService {
    private static final Logger LOGGER = Logger.getLogger(TemperatureSensorService.class.getName());

    @Override
    public void performSensorAction(SensorData sensorData) {
        LOGGER.info("Accepted sensor data " + sensorData.toString());
    }

    @Override
    public SensorType getSensorType() {
        return SensorType.TEMPERATURE;
    }

    @Override
    public int getSensorPort() {
        return PropertyConfig.TEMPERATURE_SENSOR_PORT;
    }
}
