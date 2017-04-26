package common.sw.layers.test;

import common.debug.PH;
import common.hw.comport.COMPort;
import common.hw.comport.COMPortProperties;
import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.ModBus;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.wad.WAD_AO_BUS;
import common.hw.modbus.wad.WAD_DOS_BUS;
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
