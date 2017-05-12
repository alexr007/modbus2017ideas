package jwad.modules;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.device.PortType;
import jbus.modbus.device.SignalType;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jbus.modbus.ModBus;
import jwad.WadDevType;
import jwad.channels.WAD_AIK_Channel;
import jwad.channels.WAD_Channel;
import jwad.summary.WadSummaryAIK;
import org.xembly.Directives;

/**
 * Created by alexr on 22.01.2017.
 */
public final class WAD_AIK_BUS extends WadAbstractDevice {
    public WAD_AIK_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId,
            new DeviceProperties(SignalType.Analog, PortType.Input, 4));
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder().cmdReadRegister(0x100F)),
                new RqInfo(id(),RsParsed.cmdRead,2)
            ).get(1);
    }

    @Override
    public WadDevType type() {
        return WadDevType.AIK;
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AIK_Channel(chan, this);
    }

    @Override
    public CharSequence summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAIK(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAIK(this).xmlDir();
    }
}