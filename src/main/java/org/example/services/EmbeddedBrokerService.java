package org.example.services;

import org.apache.activemq.broker.BrokerService;
import org.example.util.PropertyConfig;

import java.util.logging.Logger;

/**
 * Embedded broker service using activemq
 */
public class EmbeddedBrokerService {

    private static final Logger LOGGER = Logger.getLogger(EmbeddedBrokerService.class.getName());

    private BrokerService brokerService;

    private static class EmbeddedBrokerServiceInstance {
        public static final EmbeddedBrokerService INSTANCE = new EmbeddedBrokerService();
    }

    public static EmbeddedBrokerService getInstance() {
        return EmbeddedBrokerServiceInstance.INSTANCE;
    }

    private BrokerService createBroker() throws Exception {
        if (this.brokerService == null) {
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(PropertyConfig.MESSAGE_BROKER_ADDRESS);
            this.brokerService = broker;
        }

        return this.brokerService;
    }

    public void start() throws Exception {
        this.brokerService = this.createBroker();
        this.brokerService.start();

        LOGGER.info("Message Broker started on " + PropertyConfig.MESSAGE_BROKER_ADDRESS);
    }

    public void stop() throws Exception {
        this.brokerService.stop();
    }
}
