package common.hw;

import common.hw.modbus.response.Values;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorAnalog {
    // cable !fail
    boolean ok();
    // cable fail
    boolean fail();
    // just get value 0-65535 range
    Values.Single get();
}
