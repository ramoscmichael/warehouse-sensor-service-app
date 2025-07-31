package org.example.services.central;

import org.example.model.SensorData;
import org.example.model.SensorType;
import org.example.util.PropertyConfig;

import java.util.logging.Logger;

public class TemperatureSensorThresholdMonitor implements SensorThresholdMonitor {

    private static final Logger LOGGER = Logger.getLogger(TemperatureSensorThresholdMonitor.class.getName());

    @Override
    public SensorType getSensorType() {
        return SensorType.TEMPERATURE;
    }

    @Override
    public int getThresholdLimit() {
        return PropertyConfig.TEMPERATURE_THRESHOLD;
    }

    @Override
    public void performThresholdReachedAction(SensorData data) {
        LOGGER.info("Temperature threshold reached for sensor " + data.toString());
    }
}
