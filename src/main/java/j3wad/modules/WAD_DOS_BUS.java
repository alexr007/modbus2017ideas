package j3wad.modules;

import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j2bus.modbus.ModBus;
import j3wad.WadDevType;
import j3wad.channels.WAD_Channel;
import j3wad.channels.WAD_DOS_Channel;
import j3wad.summary.WadSummaryDOS;

import java.io.IOException;

final public class WAD_DOS_BUS extends WadAbstractDevice {
    public WAD_DOS_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.DOS,
            new DeviceProperties(SignalType.Relay, PortType.Output, 8));
        this.summary = new WadSummaryDOS(this);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DOS_Channel(chan, this);
    }

    @Override
    public int temperature() throws IOException {
        return read_(0x200A).get(1);
    }
}
