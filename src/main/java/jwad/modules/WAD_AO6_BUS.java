package jwad.modules;

import jbus.modbus.ModBus;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.device.PortType;
import jbus.modbus.device.SignalType;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.RqInfo;
import jbus.modbus.response.RsAnalyzed;
import jbus.modbus.response.RsParsed;
import jssc.SerialPortException;
import jwad.WadDevType;
import jwad.channels.WAD_AO6_Channel;
import jwad.channels.WAD_Channel;
import jwad.summary.WadSummaryAO;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO6_BUS extends WadAbstractDevice {
    public WAD_AO6_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId, WadDevType.AO6,
            new DeviceProperties(SignalType.Analog, PortType.Output, 6));
        this.summary = new WadSummaryAO(this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return read_(0x200F).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AO6_Channel(chan, this);
    }

/*
    @Override
    public CharSequence summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).txt();
    }

    @Override
    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new WadSummaryAO(this).xmlDir();
    }
*/
}
