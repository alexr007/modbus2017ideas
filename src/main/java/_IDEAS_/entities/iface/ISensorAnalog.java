package _IDEAS_.entities.iface;

import j2bus.modbus.response.Values;
import jssc.SerialPortException;

import java.io.IOException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorAnalog {
    // channel controller failsRaw
    boolean fail() throws IOException;
    // just bytes value 0-65535 range
    Values get() throws IOException;
}
