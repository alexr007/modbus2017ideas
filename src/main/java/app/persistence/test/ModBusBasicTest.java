package app.persistence.test;

import jtools.PH;
import jbus.comport.COMPort;
import jbus.comport.COMPortProperties;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.ModBus;
import jbus.modbus.response.InvalidModBusResponse;
import jwad.modules.WAD_AO_BUS;
import jwad.modules.WAD_DOS_BUS;
import jssc.SerialPortException;

/**
 * Created by alexr on 19.02.2017.
 */
public class ModBusBasicTest {
    public static void main(String[] args) throws SerialPortException, InvalidModBusFunction, InvalidModBusResponse {
        ModBus modBus = new ModBus(
            new COMPort(
                "COM25",
                new COMPortProperties(57600)
            )
        );
        System.out.println("port opened");

        long delta;
        long millis = System.currentTimeMillis();

        WAD_DOS_BUS dos = new WAD_DOS_BUS(modBus, 0x11);
        WAD_AO_BUS ao = new WAD_AO_BUS(modBus, 0x21);

        new PH("WAD-DOS-BUS temperature",dos.temperature());
        dos.channel(1).off();
        System.out.println(
            dos.channel(0).get()
        );
    }
}
