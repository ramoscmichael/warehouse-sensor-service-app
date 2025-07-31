package org.example.services.central;

import org.example.model.SensorData;
import org.example.model.SensorType;
import org.example.util.PropertyConfig;

import java.util.logging.Logger;

public class HumiditySensorThresholdMonitor implements SensorThresholdMonitor {

    private static final Logger LOGGER = Logger.getLogger(HumiditySensorThresholdMonitor.class.getName());

    @Override
    public SensorType getSensorType() {
        return SensorType.HUMIDITY;
    }

    @Override
    public int getThresholdLimit() {
        return PropertyConfig.HUMIDITY_THRESHOLD;
    }

    @Override
    public void performThresholdReachedAction(SensorData data) {
        LOGGER.info("Humidity threshold reached for sensor " + data.toString());
    }
}
