package org.example.model;

import java.io.Serializable;

public class SensorData implements Serializable {
    private final String sensorId;
    private final int value;
    private final SensorType type;

    public SensorData(String sensorId, int value, SensorType type) {
        this.sensorId = sensorId;
        this.type = type;
        this.value = value;
    }

    public String getSensorId() {
        return this.sensorId;
    }

    public int getValue() {
        return this.value;
    }

    public SensorType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "SensorData {"
                + " sensor_id=" + getSensorId()
                + ", value=" + getValue()
                + ", type=" + getType()
                + " } ";
    }
}
