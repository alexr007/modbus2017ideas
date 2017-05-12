package jwad.modules;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.device.PortType;
import jbus.modbus.device.SignalType;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jbus.modbus.ModBus;
import jwad.ModBusAbstractDevice;
import jwad.WAD_func_channel;
import jwad.WAD_func_temperature;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import jwad.channels.WAD_DOS_Channel;
import jwad.summary.WadSummaryDOS;
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
    public WAD_DOS_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId);
        properties = new DeviceProperties(SignalType.Relay, PortType.Output, 8);
    }

    @Override
    public WadDevType type() {
        return WadDevType.DOS;
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
                new RqInfo(id(),RsParsed.cmdRead,2)
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
