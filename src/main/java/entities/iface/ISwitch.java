package entities.iface;

import jbus.modbus.response.ValuesMapped;
import jwad.chanvalue.ChanValue;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISwitch {
    void on() throws Exception;
    void off() throws Exception;
    ValuesMapped<ChanValue> get();
}
