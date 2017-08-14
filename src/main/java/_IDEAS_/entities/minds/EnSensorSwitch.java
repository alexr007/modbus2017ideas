package _IDEAS_.entities.minds;

import _IDEAS_.entities.EntityAbstract;
import _IDEAS_.entities.TypeEn;
import _IDEAS_.entities.iface.ISensorDigital;
import j2bus.modbus.InvalidModBusFunction;
import j2bus.modbus.response.InvalidModBusResponse;
import j3wad.channels.WAD_Channel;
import jssc.SerialPortException;

/**
 * Created by alexr on 17.02.2017.
 *
 * Концевик
 *
 */
public class EnSensorSwitch extends EntityAbstract implements ISensorDigital {
    private final WAD_Channel channel;

    public EnSensorSwitch(WAD_Channel channel) {
        super(TypeEn.Switch);
        this.channel = channel;
    }

    @Override
    public boolean fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return channel.failsRaw().get() == 1;
    }

    @Override
    public boolean opened() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return channel.getRaw().get() == 0;
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return channel.getRaw().get() == 1;
    }
}
