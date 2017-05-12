package entities;

import jbus.modbus.response.Values;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface IPerformerRelay {
    // just get value 0-aa range
    Values.Single get();
    // just set aa
    void on();
    // just set 0
    void off();
}
