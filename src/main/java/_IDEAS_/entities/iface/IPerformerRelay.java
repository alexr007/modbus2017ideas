package _IDEAS_.entities.iface;

import j2bus.modbus.response.Values;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface IPerformerRelay {
    // just bytes value 0-1 range
    Values.Single get();
    // just set 1
    void on();
    // just set 0
    void off();
}
