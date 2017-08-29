package j3wad.modules;

import j2bus.modbus.ModBus;
import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import jssc.SerialPortException;
import j3wad.WadDevType;
import j3wad.channels.WAD_AO6_Channel;
import j3wad.channels.WAD_Channel;
import j3wad.summary.WadSummaryAO;

import java.io.IOException;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO6_BUS extends WadAbstractDevice {
    public WAD_AO6_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.AO6,
            new DeviceProperties(SignalType.Analog, PortType.Output, 6));
        this.summary = new WadSummaryAO(this);
    }

    @Override
    public int temperature() throws IOException {
        return read_(0x200F).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AO6_Channel(chan, this);
    }

/*
    @Override
    public CharSequence summaryDetailsTxt() throws ModBusInvalidResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws ModBusInvalidResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).xmlDir();
    }
*/
}
