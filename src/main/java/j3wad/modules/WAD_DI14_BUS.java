package j3wad.modules;

import j2bus.modbus.InvalidModBusFunction;
import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import j2bus.modbus.ModBus;
import j2bus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import j3wad.WadDevType;
import j3wad.channels.WAD_Channel;
import j3wad.channels.WAD_DI14_Channel;
import j3wad.summary.WadSummaryDI;

/**
 * Created by alexr on 06.02.2017.
 */
final public class WAD_DI14_BUS extends WadAbstractDevice {
    public WAD_DI14_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.DI14,
            new DeviceProperties(SignalType.Digital, PortType.Input, 15));
        this.summary = new WadSummaryDI(this);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DI14_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse, InvalidModBusFunction {
        throw new InvalidModBusFunction();
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
