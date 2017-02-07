package common.hardware;

import common.modbus.response.Values;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface IPerformerRelay {
    // just get value 0-1 range
    Values.Single get();
    // just set 1
    void on();
    // just set 0
    void off();

}
