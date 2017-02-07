package common.modbus.wad;

import common.modbus.device.DeviceProperties;
import common.modbus.device.PortType;
import common.modbus.device.SignalType;
import common.modbus.response.*;
import jssc.SerialPortException;
import persistence.ModBus;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO_BUS extends ModBusAbstractDevice implements WAD_func_channel, WAD_func_temperature {
    public WAD_AO_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId);
        properties = new DeviceProperties(SignalType.Analog, PortType.Output, 4);
    }

    @Override
    public int temperature() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder.cmdReadRegister(0x200B)),
                new RqInfo(deviceId,RsParsed.cmdRead,2)
            ).get(1);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_AO_Channel(chan, this);
    }
}
