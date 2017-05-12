package jwad.modules;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.device.PortType;
import jbus.modbus.device.SignalType;
import jbus.modbus.ModBus;
import jbus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import jwad.ModBusAbstractDevice;
import jwad.WAD_func_channel;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import jwad.channels.WAD_DI14_Channel;
import jwad.summary.WadSummaryDI;
import org.xembly.Directives;

/**
 * Created by alexr on 06.02.2017.
 */
final public class WAD_DI14_BUS extends ModBusAbstractDevice implements WAD_func_channel {
    /*
     * inherited:
     *
     * private final ModBus modbus;
     * protected final ModBusRequestBuilder builder;
     * protected final int deviceId;
     *
     */
    public WAD_DI14_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId);
        properties = new DeviceProperties(SignalType.Digital, PortType.Input, 15);
    }

    @Override
    public WadDevType type() {
        return WadDevType.DI14;
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DI14_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse, InvalidModBusFunction {
        throw new InvalidModBusFunction();
    }

    @Override
    public String summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDI(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDI(this).xmlDir();
    }
}
