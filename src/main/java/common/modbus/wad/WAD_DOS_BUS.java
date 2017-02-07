package common.modbus.wad;

import common.modbus.device.DeviceProperties;
import common.modbus.device.PortType;
import common.modbus.device.SignalType;
import common.modbus.response.*;
import jssc.SerialPortException;
import persistence.ModBus;

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
}
