package entities.minds;

import entities.EntityAbstract;
import entities.TypeEn;
import entities.iface.ISensorAnalog;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jwad.channels.WAD_Channel;
import jssc.SerialPortException;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnSensor extends EntityAbstract implements ISensorAnalog {
    private final WAD_Channel channel;

    public EnSensor(WAD_Channel channel) {
        super(TypeEn.Sensor);
        this.channel = channel;
    }

    @Override
    public boolean fail() throws InvalidModBusFunction, SerialPortException, InvalidModBusResponse {
        return channel.failsRaw().get() == 1;
    }
    @Override
    public Values get() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return channel.getRaw();
    }
}
