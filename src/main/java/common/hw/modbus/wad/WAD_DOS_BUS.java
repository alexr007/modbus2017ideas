package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.device.DeviceProperties;
import common.hw.modbus.device.PortType;
import common.hw.modbus.device.SignalType;
import common.hw.modbus.response.*;
import jssc.SerialPortException;
import common.hw.modbus.ModBus;
import org.xembly.Directives;

final public class WAD_DOS_BUS extends ModBusAbstractDevice implements WAD_func_channel, WAD_func_temperature {
    /*
     * inherited:
     *
     * private final ModBus modbus;
     * protected final ModBusRequestBuilder builder;
     * protected final int deviceId;
     *
     */
    public WAD_DOS_BUS(ModBus modbus, int deviceId, WadDevType type) {
        super(modbus, deviceId, type);
        properties = new DeviceProperties(SignalType.Relay, PortType.Output, 8);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DOS_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder.cmdReadRegister(0x200A)),
                new RqInfo(deviceId,RsParsed.cmdRead,2)
            ).get(1);
    }

    @Override
    public String summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDOS(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDOS(this).xmlDir();
    }
}
