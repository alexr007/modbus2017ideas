package app.persistence;

import app.persistence.init.chan.ModBusChannels;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;
import jbus.comport.COMPort;
import jbus.comport.COMPortFake;
import jbus.comport.COMPortProperties;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.channels.WAD_Channel;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcore {
    private final ModBusDevices devices;
    private final ModBusChannels channels;
    private final ModBus bus;

    public BIOcore(String comName) throws Exception {
        this.bus = new ModBus(
            //new COMPortFake(
            new COMPort(
                comName,
                new COMPortProperties(SerialPort.BAUDRATE_57600)
            )
        );
/*
        this.devices = DeviceBuilder.buildEcoAlliance(this.bus);
        this.channels = ChannelBuilder.buildEcoAlliance(this.devices);
*/
        this.devices = DeviceBuilder.buildTestEnvironment(this.bus);
        this.channels = ChannelBuilder.buildTestEnvironment(this.devices);
    }

    public ModBus bus() {
        return bus;
    }

    public ModBusDevices devices() {
        return devices;
    }

    public ModBusChannels channels() {
        return channels;
    }

    public WadAbstractDevice dev(DevName name) {
        return devices.get(name);
    }

    public WadAbstractDevice dev(String name) {
        return devices.get(name);
    }

    public WAD_Channel chan(ChanName name) {
        return channels.get(name);
    }

    public WAD_Channel chan(String name) {
        return channels.get(name);
    }

    public ArrayList<Triplet> devListTriplet() {
        return devices.triplet();
    }

    public ArrayList<Quartet> chanListQuartet() {
        return channels.quartet();
    }

    public void finish() throws SerialPortException {
        bus().finish();
    }

}
