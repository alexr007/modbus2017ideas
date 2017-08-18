package j4core;

import j4core.build.ChannelBuilded;
import j4core.build.DeviceBuilded;
import j4core.entity.chan.ModBusChannels;
import j4core.entity.dev.ModBusDevices;
import j4core.persistence.Persistence;
import constants.ChanName;
import constants.DevName;
import j2bus.comport.COMPort;
import j2bus.comport.COMPortProperties;
import j2bus.modbus.ModBus;
import j3wad.modules.WadAbstractDevice;
import j3wad.channels.WAD_Channel;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.util.List;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcore {
    private final Persistence persistence;
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
        this.devices = new ModBusDevices(this.bus, new DeviceBuilded().devices());
        final ChannelBuilded cb = new ChannelBuilded();
        this.channels = new ModBusChannels(devices, cb.list());
        this.persistence = new Persistence(cb.mapChanCount());
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

    public Persistence persistence() {
        return persistence;
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

    public List<Triplet> devListTriplet() {
        return devices.listDetailsTriplet();
    }

    public List<Quartet> chanListQuartet() {
        return channels.listDetailsQuartet();
    }

    public void finish()  {
        try {
            finishUnsafe();
        } catch (SerialPortException e) {
            throw new IllegalArgumentException("BUS ERROR");
        }
    }

    public void finishUnsafe() throws SerialPortException {
        bus().finish();
    }

}
