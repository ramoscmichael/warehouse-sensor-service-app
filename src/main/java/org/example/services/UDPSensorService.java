package org.example.services;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.SensorData;
import org.example.services.warehouse.SensorService;
import org.example.util.PropertyConfig;

import javax.jms.*;
import java.lang.IllegalStateException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * UDP server class that based on SensorService interface information
 */
public class UDPSensorService {

    private final SensorService service;

    private DatagramSocket socket;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    private boolean active = true;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public UDPSensorService(SensorService service) {
        this.service = service;
    }

    public void init() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(PropertyConfig.MESSAGE_BROKER_ADDRESS);
        factory.setTrustAllPackages(true);

        this.connection = factory.createConnection();
        this.connection.start();

        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = this.session.createQueue(PropertyConfig.MESSAGE_QUEUE_ID);
        this.producer = this.session.createProducer(queue);
        this.socket = new DatagramSocket(this.service.getSensorPort());
    }

    public void start() {
        try {
            this.active = true;
            byte[] buffer = new byte[1024];
            while (this.active) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                this.executorService.submit(() -> {
                    try {
                        String message = new String(packet.getData(), 0, packet.getLength());
                        SensorData sensorData = service.parse(message);
                        service.performSensorAction(sensorData);
                        this.producer.send(this.session.createObjectMessage(sensorData));
                    } catch (Exception e) {
                        throw new IllegalStateException(e.getMessage(), e);
                    }
                });
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    public void stop() {
        if (this.socket != null) {
            this.active = false;
            this.executorService.shutdown();

            try { this.connection.stop(); } catch (Exception ignored) {;}
            try { this.socket.close(); } catch (Exception ignored) {;}
            try { this.connection.close(); } catch (Exception ignored) {;}
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " {"
                + " sensorService=" + this.service.getClass().getName()
                + " ,port=" + this.service.getSensorPort()
                + " }";
    }
}
