package org.example.services;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.SensorData;
import org.example.model.SensorType;
import org.example.services.central.SensorThresholdMonitor;
import org.example.util.PropertyConfig;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Central service that accepts SensorData and analyze value threshold base on registered threshold actions
 */
public class CentralService {

    private static final Logger LOGGER = Logger.getLogger(CentralService.class.getName());

    private Connection connection;

    private final Map<SensorType, SensorThresholdMonitor> sensorThresholdMonitors = new HashMap<>();

    private static class CentralServiceInstance {
        private static final CentralService INSTANCE = new CentralService();
    }

    public static CentralService getInstance() {
        return CentralServiceInstance.INSTANCE;
    }

    public CentralService addSensorThresholdMonitor(SensorThresholdMonitor monitor) {
        this.sensorThresholdMonitors.put(monitor.getSensorType(), monitor);
        return this;
    }

    /**
     * initialize JMS and consumer listener
     */
    public void start() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(PropertyConfig.MESSAGE_BROKER_ADDRESS);
        factory.setTrustAllPackages(true);

        this.connection = factory.createConnection();
        this.connection.start();

        Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue(PropertyConfig.MESSAGE_QUEUE_ID);

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this::listen);

        LOGGER.info("Central services started");
    }

    private void listen(Message message) {
        if (message instanceof ObjectMessage) {
           try {
               SensorData data = message.getBody(SensorData.class);
               SensorThresholdMonitor thresholdListener = this.sensorThresholdMonitors.get(data.getType());
               if (thresholdListener != null && thresholdListener.isThresholdReached(data)) {
                   thresholdListener.performThresholdReachedAction(data);
               }
               // perform data saving

               LOGGER.info("Saving sensor data " + data.toString());
           } catch (Exception ex) {
               LOGGER.severe("Error in parsing sensor data: " +  ex.getMessage());
           }
        }
    }

    public void stop() throws Exception {
        try { this.connection.stop(); } catch (Exception ignored) {;}
        try { this.connection.close(); } catch (Exception ignored) {;}
    }

}
