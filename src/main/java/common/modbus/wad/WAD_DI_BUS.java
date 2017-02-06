package common.modbus.wad;

import common.modbus.device.DeviceProperties;
import common.modbus.device.PortType;
import common.modbus.device.SignalType;
import common.modbus.response.InvalidModBusResponse;
import common.modbus.response.RqInfo;
import common.modbus.response.RsAnalyzed;
import common.modbus.response.RsParsed;
import jssc.SerialPortException;
import persistence.ModBus;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_DI_BUS extends ModBusAbstractDevice implements WAD_func_channel, WAD_func_temperature {
    /*
     * inherited:
     *
     * private final ModBus modbus;
     * protected final ModBusRequestBuilder builder;
     * protected final int deviceId;
     *
     */
    public WAD_DI_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId);
        properties = new DeviceProperties(SignalType.Digital, PortType.Input, 8);
    }

    @Override
    public Channel channel(int chan) {
        return new WAD_DI_Channel(chan, this);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder.cmdReadRegister(0x200C)),
                new RqInfo(deviceId, RsParsed.cmdRead,2)
            ).get(1);
    }
}
