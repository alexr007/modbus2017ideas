package entities.iface;

import jbus.modbus.response.Values;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface IPerformerAnalog {
    // just bytes value 0-65535 range
    Values.Single get();
    // just set value 0-65535 range
    void set(Values.Single value);
}
