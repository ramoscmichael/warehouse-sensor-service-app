package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    private final DatagramSocket socket;

    public UDPClient() throws Exception {
        this.socket = new DatagramSocket();
    }

    public void sendSensorData(int serverPort, String message) throws Exception {
        InetAddress serverAddress = InetAddress.getByName("localhost");
        // Create packet to send
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        this.socket.send(sendPacket);
    }

}
