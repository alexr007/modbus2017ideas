package common.modbus.wad;

import common.modbus.response.InvalidModBusResponse;
import common.modbus.response.InvalidModBusResponseCRC;
import common.modbus.response.InvalidModBusResponseLength;
import jssc.SerialPortException;

/**
 * Created by alexr on 21.01.2017.
 */
public interface WAD_func_temperature {
    // device temperature
    public int temperature() throws SerialPortException, InvalidModBusResponse;
}
