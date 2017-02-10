package common.hw.modbus.wad;

import common.hw.modbus.device.DeviceProperties;
import common.hw.modbus.device.PortType;
import common.hw.modbus.device.SignalType;
import common.hw.modbus.response.*;
import jssc.SerialPortException;
import common.hw.modbus.ModBus;

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
