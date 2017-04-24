package common.sw.decision;

import common.hw.modbus.response.Values;

/**
 * Created by alexr on 24.04.2017.
 */
public interface PortReader {
    Values get(CharSequence name);
}
