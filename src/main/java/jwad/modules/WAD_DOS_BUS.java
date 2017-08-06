package jwad.modules;

import jbus.modbus.device.DeviceProperties;
import jbus.modbus.device.PortType;
import jbus.modbus.device.SignalType;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jbus.modbus.ModBus;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import jwad.channels.WAD_DOS_Channel;
import jwad.summary.WadSummaryDOS;

final public class WAD_DOS_BUS extends WadAbstractDevice {
    public WAD_DOS_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.DOS,
            new DeviceProperties(SignalType.Relay, PortType.Output, 8));
        this.summary = new WadSummaryDOS(this);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DOS_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder().cmdReadRegister(0x200A)),
                new RqInfo(id(),RsParsed.cmdRead,2)
            ).get(1);
    }
/*

    @Override
    public CharSequence summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDOS(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryDOS(this).xmlDir();
    }
*/
}
