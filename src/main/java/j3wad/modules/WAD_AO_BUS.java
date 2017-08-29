package j3wad.modules;

import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j2bus.modbus.ModBus;
import j3wad.WadDevType;
import j3wad.channels.WAD_AO_Channel;
import j3wad.channels.WAD_Channel;
import j3wad.summary.WadSummaryAO;

import java.io.IOException;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO_BUS extends WadAbstractDevice {
    public WAD_AO_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.AO,
            new DeviceProperties(SignalType.Analog, PortType.Output, 4));
        this.summary = new WadSummaryAO(this);
    }

    @Override
    public int temperature() throws IOException {
        return read_(0x200B).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AO_Channel(chan, this);
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
