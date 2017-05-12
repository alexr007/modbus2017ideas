package common.sw.layers;

import jbus.comport.COMPort;
import jbus.comport.COMPortProperties;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.channels.WAD_Channel;
import jwad.WadDevType;
import constants.Id;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Set;

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
/*
                add(new Triplet<>("AIK21", WadDevType.AIK, Id.x21));
                add(new Triplet<>("AIK22", WadDevType.AIK, Id.x22));
                add(new Triplet<>("DI11", WadDevType.DI14, Id.x11));
                add(new Triplet<>("DI12", WadDevType.DI14, Id.x12));
*/
                add(new Triplet<>("DI13", WadDevType.DI14, Id.x13));
                add(new Triplet<>("DI14", WadDevType.DI14, Id.x14));
/*
                add(new Triplet<>("DOS31", WadDevType.DOS, Id.x31));
                add(new Triplet<>("DOS32", WadDevType.DOS, Id.x32));
                add(new Triplet<>("DOS33", WadDevType.DOS, Id.x33));
*/
                add(new Triplet<>("DOS34", WadDevType.DOS, Id.x34));
                add(new Triplet<>("DOS35", WadDevType.DOS, Id.x35));
/*
                add(new Triplet<>("AO41", WadDevType.AO6, Id.x41));
                add(new Triplet<>("AO42", WadDevType.AO6, Id.x42));
*/
            }}
        );
        this.channels = new ModBusChannels(
            this.devices,
            new ArrayList<Triplet<CharSequence, CharSequence, Integer>>() {{
                // just channels definition
//                add(new Triplet<>("CH0","DEV11", Ch.n0));
            }}
        );
    }

    public WadAbstractDevice dev(CharSequence name) {
        try {
            return devices.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Module Name NotFound:%s",name));
        }
    }

    public WAD_Channel chan(CharSequence name) throws Exception {
        try {
            return channels.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Channel Name NotFound:%s",name));
        }
    }

    public Set<CharSequence> devList() {
        return devices.list();
    }

    public ArrayList<Triplet> devListTriplet() {
        return devices.triplet();
    }

    public ModBus modBus() {
        return modBus;
    }

    public void finish() throws SerialPortException {
        modBus().finish();
    }
}
