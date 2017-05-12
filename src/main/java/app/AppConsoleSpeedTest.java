package app;

import jbus.comport.COMPort;
import jbus.comport.COMPortProperties;
import jbus.modbus.ModBus;
import jbus.modbus.response.InvalidModBusResponse;
import jwad.modules.WAD_DOS_BUS;
import jwad.WadDevType;
import constants.Id;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Created by alexr on 11.04.2017.
 */
public class AppConsoleSpeedTest {
    private final String portName;

    public AppConsoleSpeedTest(String portName) {
        this.portName = portName;
    }

    public void run() throws SerialPortException, InterruptedException {
        ModBus modBus = new ModBus(
            new COMPort(
                portName,
                new COMPortProperties(SerialPort.BAUDRATE_57600)
            )
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
            } catch (SerialPortException e) {
                e.printStackTrace();
            } catch (InvalidModBusResponse invalidModBusResponse) {
                invalidModBusResponse.printStackTrace();
            }
            //System.out.println(t);

        delta = System.currentTimeMillis() - millis;
        System.out.println(
            String.format("Reqest per second %f", (float)count/delta*1000)
        );
        modBus.finish();
    }
}
