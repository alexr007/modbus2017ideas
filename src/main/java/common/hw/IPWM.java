package common.hw;

import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
public interface IPWM {
    void run(int value) throws Exception;
}
