package j3wad.modules;

import j2bus.modbus.ModBus;
import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import j2bus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import j3wad.WadDevType;
import j3wad.channels.WAD_AO6_Channel;
import j3wad.channels.WAD_Channel;
import j3wad.summary.WadSummaryAO;

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
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return read_(0x200F).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AO6_Channel(chan, this);
    }

/*
    @Override
    public CharSequence summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).xmlDir();
    }
*/
}
