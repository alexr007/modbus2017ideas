package tests.some;

import common.hw.comport.COMPort;
import common.hw.comport.COMPortProperties;
import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.ModBus;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.wad.ModBusInvalidFunction;
import common.hw.modbus.wad.WAD_DOS_BUS;
import constants.Id;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by alexr on 11.04.2017.
 */
public class Test_DOS_Timing {
    public void run(String portName) throws SerialPortException, ModBusInvalidFunction, InterruptedException {
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
