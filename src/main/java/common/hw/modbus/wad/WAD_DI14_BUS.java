package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.device.DeviceProperties;
import common.hw.modbus.device.PortType;
import common.hw.modbus.device.SignalType;
import common.hw.modbus.ModBus;
import common.hw.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
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
    public WAD_Channel channel(int chan) {
        return new WAD_DI14_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse, InvalidModBusFunction {
        throw new InvalidModBusFunction();
    }

    @Override
    public String summaryDetails() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WAD_DI_Summary(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WAD_DI_Summary(this).xmlDir();
    }
}
