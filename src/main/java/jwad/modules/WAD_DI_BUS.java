package jwad.modules;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.device.PortType;
import jbus.modbus.device.SignalType;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.RqInfo;
import jbus.modbus.response.RsAnalyzed;
import jbus.modbus.response.RsParsed;
import jssc.SerialPortException;
import jbus.modbus.ModBus;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import jwad.channels.WAD_DI_Channel;
import jwad.summary.WadSummaryDI;
import org.xembly.Directives;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_DI_BUS extends WadAbstractDevice {
    public WAD_DI_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId,
            new DeviceProperties(SignalType.Digital, PortType.Input, 8));
    }

    @Override
    public WadDevType type() {
        return WadDevType.DI;
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DI_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder().cmdReadRegister(0x200C)),
                new RqInfo(id(), RsParsed.cmdRead,2)
            ).get(1);
    }

    @Override
    public CharSequence summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDI(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDI(this).xmlDir();
    }
}
