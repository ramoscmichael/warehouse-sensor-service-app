package org.example;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.model.SensorData;
import org.example.services.CentralService;
import org.example.services.EmbeddedBrokerService;
import org.example.services.WarehouseServices;
import org.example.services.warehouse.HumiditySensorService;
import org.example.services.warehouse.SensorService;
import org.example.util.PropertyConfig;

/**
 * Unit test for simple App.
 */
public class Test
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Test(String testName ) throws Exception
    {
        super( testName );

    }

    /**
     * @return the suite of tests being tested
     */
    public static junit.framework.Test suite()
    {
        return new TestSuite( Test.class );
    }


    /**
     * Simulate test in sending sensor data
     */
    public void test() throws Exception {
        UDPClient client = new UDPClient();

        new EmbeddedBrokerServiceApp().start();
        new CentralServiceApp().start();
        new WarehouseServicesApp().start();

        client.sendSensorData(PropertyConfig.TEMPERATURE_SENSOR_PORT, "sensor_id=t1;value=30");
        Thread.sleep(1000);

        client.sendSensorData(PropertyConfig.HUMIDITY_SENSOR_PORT, "sensor_id=t1;value=30");
        Thread.sleep(1000);

        client.sendSensorData(PropertyConfig.TEMPERATURE_SENSOR_PORT, "sensor_id=t1;value=60");
        Thread.sleep(1000);

        client.sendSensorData(PropertyConfig.HUMIDITY_SENSOR_PORT, "sensor_id=t1;value=60");
        Thread.sleep(1000);

        client.sendSensorData(PropertyConfig.HUMIDITY_SENSOR_PORT, "sensor_id=t1;value=50");
        Thread.sleep(1000);
    }

}
