package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.device.DeviceProperties;
import common.hw.modbus.device.PortType;
import common.hw.modbus.device.SignalType;
import common.hw.modbus.response.*;
import jssc.SerialPortException;
import common.hw.modbus.ModBus;
import org.xembly.Directives;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AIK_BUS extends ModBusAbstractDevice implements WAD_func_channel, WAD_func_temperature {
    /*
     * inherited:
     *
     * private final ModBus modbus;
     * protected final ModBusRequestBuilder builder;
     * protected final int deviceId;
     *
     */
    public WAD_AIK_BUS(ModBus modbus, int deviceId, WadDevType type) {
        super(modbus, deviceId, type);
        properties = new DeviceProperties(SignalType.Analog, PortType.Input, 4);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder.cmdReadRegister(0x100F)),
                new RqInfo(deviceId,RsParsed.cmdRead,2)
            ).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AIK_Channel(chan, this);
    }

    @Override
    public String summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAIK(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAIK(this).xmlDir();
    }
}
