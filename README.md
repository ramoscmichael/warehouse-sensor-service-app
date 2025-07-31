## Central and Warehouse sensor service
--- 
There are three main service app

EmbeddedBrokerServiceApp.java (This will start the Message Broker Server) <br/>
CentralServiceApp.java (This will start the CentralService server) <br/>
WarehouseServiceApp.java (This will start the WarehouseService servers) <br/>

---
### CentralService.java
Accepts Sensor Data from message broker and execute threshold monitors implementations

HumiditySensorThresholdMonitor.java <br/>
TemperatureSensorThresholdMonitor.java <br/>

---
### UDPSensorService.java
Accepts Sensor Data using UDP packets and send data the message broker server base on the SensorService implementations

HumiditySensorService.java <br/>
TemperatureSensorService.java <br/>

---
### EmbeddedBrokerService.java
Acts as a message broker service using ActiveMQ
