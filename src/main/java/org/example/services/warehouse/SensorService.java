package org.example.services.warehouse;

import org.example.model.SensorData;
import org.example.model.SensorType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public interface SensorService {

    default SensorData parse(String message) {

        Map<String, String> mapValue = Arrays.stream(message.split(";"))
                .map(s -> s.split("=", 2))
                .filter(s -> s.length == 2)
                .collect(Collectors.toMap(key -> key[0], val -> val[1]));

        return new SensorData( mapValue.get("sensor_id"), Integer.parseInt(mapValue.get("value")), getSensorType() );
    }

    void performSensorAction(SensorData sensorData);

    SensorType getSensorType();
    int getSensorPort();
}
