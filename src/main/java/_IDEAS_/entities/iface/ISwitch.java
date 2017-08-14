package _IDEAS_.entities.iface;

import j2bus.modbus.response.ValuesMapped;
import j3wad.chanvalue.ChanValue;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISwitch {
    void on() throws Exception;
    void off() throws Exception;
    ValuesMapped<ChanValue> get();
}
