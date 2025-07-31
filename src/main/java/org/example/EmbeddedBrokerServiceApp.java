package org.example;

import org.example.services.EmbeddedBrokerService;

public class EmbeddedBrokerServiceApp {

    public void start() throws Exception {
        EmbeddedBrokerService.getInstance().start();
    }

    public static void main( String[] args ) throws Exception {
        new EmbeddedBrokerServiceApp().start();
    }
}
