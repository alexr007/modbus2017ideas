package _IDEAS_.entities.iface;

import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorDigital {
    // cable failsRaw
    boolean fail();
    // normal state
    boolean opened();
    // shorted state
    boolean closed();
}
