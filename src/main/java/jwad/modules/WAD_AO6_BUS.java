package jwad.modules;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.ModBus;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.device.PortType;
import jbus.modbus.device.SignalType;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.RqInfo;
import jbus.modbus.response.RsAnalyzed;
import jbus.modbus.response.RsParsed;
import jssc.SerialPortException;
import jwad.ModBusAbstractDevice;
import jwad.WAD_func_channel;
import jwad.WAD_func_temperature;
import jwad.WadDevType;
import jwad.channels.WAD_AO6_Channel;
import jwad.channels.WAD_Channel;
import jwad.summary.WadSummaryAO;
import org.xembly.Directives;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO6_BUS extends ModBusAbstractDevice implements WAD_func_channel, WAD_func_temperature {
    public WAD_AO6_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId);
        properties = new DeviceProperties(SignalType.Analog, PortType.Output, 6);
    }

    @Override
    public WadDevType type() {
        return WadDevType.AO6;
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder.cmdReadRegister(0x200F)),
                new RqInfo(id(),RsParsed.cmdRead,2)
            ).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AO6_Channel(chan, this);
    }

    @Override
    public String summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).xmlDir();
    }
}
