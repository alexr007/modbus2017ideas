package app.persistence;

import app.persistence.init.ModBusChannels;
import app.persistence.init.ModBusDevices;
import jbus.comport.COMPort;
import jbus.comport.COMPortProperties;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.channels.WAD_Channel;
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
            modBus,
            //DeviceBuilded.buildEcoAlliance()
            DeviceBuilded.buildTestEnvironment()
        );
        this.channels = new ModBusChannels(
            this.devices,
            new ArrayList<Triplet<CharSequence, CharSequence, Integer>>() {{
/*
                add(new Triplet<>("CH11",Dv.DOS1, Ch.n1));
                add(new Triplet<>("CH12",Dv.DOS1, Ch.n2));
                add(new Triplet<>("CH13",Dv.DOS1, Ch.n3));

                add(new Triplet<>("CH21",Dv.DI1, Ch.n4));
                add(new Triplet<>("CH22",Dv.DI1, Ch.n5));
                add(new Triplet<>("CH23",Dv.DI1, Ch.n6));

                add(new Triplet<>("CH31",Dv.AO2, Ch.n1));
                add(new Triplet<>("CH32",Dv.AO2, Ch.n2));
                add(new Triplet<>("CH33",Dv.AO2, Ch.n3));
*/
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

    public ArrayList<Triplet> chanListTriplet() {
        return channels.triplet();
    }

    public ModBus modBus() {
        return modBus;
    }

    public void finish() throws SerialPortException {
        modBus().finish();
    }
}
