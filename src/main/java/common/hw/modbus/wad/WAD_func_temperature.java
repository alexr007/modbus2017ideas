package common.hw.modbus.wad;

import common.hw.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;

/**
 * Created by alexr on 21.01.2017.
 */
public interface WAD_func_temperature {
    // device temperature
    public int temperature() throws SerialPortException, InvalidModBusResponse;
}
