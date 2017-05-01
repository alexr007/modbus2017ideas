package common.sw.layers;

import common.hw.comport.COMPort;
import common.hw.comport.COMPortProperties;
import common.hw.modbus.ModBus;
import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.WAD_Channel;
import common.hw.modbus.wad.WadDevType;
import constants.Ch;
import constants.Id;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.util.ArrayList;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcore {
    private final ModBusDevices devices;
    private final ModBusChannels channels;
    private final ModBus modBus;
    private final COMPort comPort;

    public BIOcore(String comName) throws Exception {
        this.comPort = new COMPort(
            comName,
            new COMPortProperties(SerialPort.BAUDRATE_57600)
        );
        this.modBus = new ModBus(
            this.comPort
        );
        this.devices = new ModBusDevices(
            this.modBus,
            new ArrayList<Triplet<String, WadDevType, Integer>>() {{
                // just device definition
                add(new Triplet<>("DEV11", WadDevType.DI14, Id.x11));
                add(new Triplet<>("DEV21", WadDevType.AIK, Id.x21));
                add(new Triplet<>("DEV31", WadDevType.DOS, Id.x31));
                add(new Triplet<>("DEV41", WadDevType.AO6, Id.x41));
            }}
        );
        this.channels = new ModBusChannels(
            this.devices,
            new ArrayList<Triplet<CharSequence, CharSequence, Integer>>() {{
                // just channels definition
                add(new Triplet<>("CH0","DEV11", Ch.n0));
                add(new Triplet<>("CH1","DEV11", Ch.n1));
                add(new Triplet<>("CH2","DEV11", Ch.n2));
                add(new Triplet<>("CH3","DEV11", Ch.n3));
                add(new Triplet<>("CH4","DEV11", Ch.n4));
                add(new Triplet<>("CH5","DEV11", Ch.n5));
                add(new Triplet<>("CH6","DEV11", Ch.n6));
                add(new Triplet<>("CH7","DEV11", Ch.n7));
                add(new Triplet<>("CH8","DEV11", Ch.n8));
            }}
        );
    }

    public ModBusAbstractDevice dev(CharSequence name) throws Exception {
        return devices.get(name);
    }

    public WAD_Channel chan(CharSequence name) throws Exception {
        return channels.get(name);
    }

    public ModBus modBus() {
        return modBus;
    }

    public void finish() throws SerialPortException {
        modBus().finish();
    }
}
