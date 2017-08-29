package _IDEAS_.entities.minds;

import _IDEAS_.entities.EntityAbstract;
import _IDEAS_.entities.TypeEn;
import _IDEAS_.entities.iface.ISensorAnalog;
import j2bus.modbus.response.Values;
import j3wad.channels.WAD_Channel;
import jssc.SerialPortException;

import java.io.IOException;

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
    public boolean fail() throws IOException {
        return channel.failsRaw().get() == 1;
    }
    @Override
    public Values get() throws IOException {
        return channel.getRaw();
    }
}
