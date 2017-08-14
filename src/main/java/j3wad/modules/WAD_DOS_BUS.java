package j3wad.modules;

import j2bus.modbus.device.DeviceProperties;
import j2bus.modbus.device.PortType;
import j2bus.modbus.device.SignalType;
import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j2bus.modbus.ModBus;
import j3wad.WadDevType;
import j3wad.channels.WAD_Channel;
import j3wad.channels.WAD_DOS_Channel;
import j3wad.summary.WadSummaryDOS;

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

/*
    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder().cmdReadRegister(0x200A)),
                new RqInfo(id(),RsParsed.cmdRead,2)
            ).get(1);
    }
*/
    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return read_(0x200A).get(1);
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
