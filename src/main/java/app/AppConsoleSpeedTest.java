package app;

import j2bus.comport.COMPort;
import j2bus.comport.COMPortProperties;
import j2bus.modbus.ModBus;
import j2bus.modbus.response.InvalidModBusResponse;
import j3wad.modules.WAD_DOS_BUS;
import constants.Id;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;

/**
 * Created by alexr on 11.04.2017.
 */
public class AppConsoleSpeedTest {
    private final String portName;

    public AppConsoleSpeedTest(String portName) {
        this.portName = portName;
    }

    public void run() throws IOException {
        ModBus modBus = new ModBus(
            new COMPort(
                portName,
                new COMPortProperties(SerialPort.BAUDRATE_57600),
                500)
        );

        System.out.println("port opened");
        WAD_DOS_BUS device = new WAD_DOS_BUS(modBus, Id.x31);

        long delta;
        long millis = System.currentTimeMillis();

        int count = 1000;
        int t = 0;
            try {
                for (int i = 1; i <= count; i++) {
                    t += device.temperature();
                }
            } catch (SerialPortException | InvalidModBusResponse e) {
                e.printStackTrace();
            }
        //System.out.println(tests.t);

        delta = System.currentTimeMillis() - millis;
        System.out.println(
            String.format("Reqest per second %f", (float)count/delta*1000)
        );
        modBus.finish();
    }
}
