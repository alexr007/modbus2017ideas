package j3wad.modules;

import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import j2bus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import j2bus.modbus.ModBus;
import j3wad.WadDevType;
import j3wad.channels.WAD_Channel;
import j3wad.channels.WAD_DI_Channel;
import j3wad.summary.WadSummaryDI;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_DI_BUS extends WadAbstractDevice {
    public WAD_DI_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.DI,
            new DeviceProperties(SignalType.Digital, PortType.Input, 8));
        this.summary = new WadSummaryDI(this);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DI_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return read_(0x200C).get(1);
    }
/*

    @Override
    public CharSequence summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDI(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDI(this).xmlDir();
    }
*/
}
