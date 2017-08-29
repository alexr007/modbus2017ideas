package j3wad.modules;

import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j2bus.modbus.ModBus;
import j3wad.WadDevType;
import j3wad.channels.WAD_AIK_Channel;
import j3wad.channels.WAD_Channel;
import j3wad.summary.WadSummaryAIK;

import java.io.IOException;

/**
 * Created by alexr on 22.01.2017.
 */
public final class WAD_AIK_BUS extends WadAbstractDevice {
    public WAD_AIK_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.AIK,
            new DeviceProperties(SignalType.Analog, PortType.Input, 4));
        this.summary = new WadSummaryAIK(this);
    }

    @Override
    public int temperature() throws IOException {
        return read_(0x100F).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AIK_Channel(chan, this);
    }
}
