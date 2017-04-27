package common.sw.layers.test;

import common.hw.comport.COMPort;
import common.hw.comport.COMPortProperties;
import common.hw.modbus.ModBus;
import common.hw.modbus.wad.WadDevType;
import common.sw.layers.ModBusChannels;
import common.sw.layers.ModBusDevices;
import constants.Ch;
import constants.Dv;
import constants.Id;
import jssc.SerialPort;
import org.javatuples.Triplet;

import java.util.ArrayList;

/**
 * Created by alexr on 27.04.2017.
 */
public class ModBusChannelsTest {
    public void test() throws Exception {

        ModBusChannels channels = new ModBusChannels(
            new ModBusDevices(
                new ModBus(
                    new COMPort(
                        Dv.COM25,
                        new COMPortProperties(SerialPort.BAUDRATE_57600)
                    )
                ),
                new ArrayList<Triplet<String, WadDevType, Integer>>() {{
                    // just device definition
                    add(new Triplet<>("DEV1", WadDevType.AIK, Id.x11));
                    add(new Triplet<>("DEV2", WadDevType.AO6, Id.x12));
                    add(new Triplet<>("DEV3", WadDevType.DI, Id.x13));
                    add(new Triplet<>("DEV4", WadDevType.DI14, Id.x14));
                    add(new Triplet<>("DEV5", WadDevType.DOS, Id.x15));
                }}
            ),
            new ArrayList<Triplet<CharSequence, CharSequence, Integer>>() {{
                // just channels definition
                add(new Triplet<>("CH1","DEV1", Ch.n1));
                add(new Triplet<>("CH2","DEV1", Ch.n2));
                add(new Triplet<>("CH3","DEV1", Ch.n2));
                add(new Triplet<>("CH4","DEV1", Ch.n2));
                add(new Triplet<>("CH5","DEV1", Ch.n2));
                add(new Triplet<>("CH6","DEV1", Ch.n2));
                add(new Triplet<>("CH7","DEV1", Ch.n2));
            }}

        );
        channels.add("CH8", "DEV1", Ch.n2);
        System.out.println(
            channels.toString()
        );
        channels.finish();
    }
}
