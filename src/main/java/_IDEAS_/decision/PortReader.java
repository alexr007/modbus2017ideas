package _IDEAS_.decision;

import j2bus.modbus.response.Values;

/**
 * Created by alexr on 24.04.2017.
 */
public interface PortReader {
    Values get(CharSequence name);
}
