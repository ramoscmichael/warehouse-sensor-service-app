package org.example.services.central;

import org.example.model.SensorData;
import org.example.model.SensorType;

public interface SensorThresholdMonitor {
    int getThresholdLimit();
    SensorType getSensorType();

    void performThresholdReachedAction(SensorData data);
    default boolean isThresholdReached(SensorData data) {
        return data.getValue() > this.getThresholdLimit();
    }
}
