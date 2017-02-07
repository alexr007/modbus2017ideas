package common.hardware;

import common.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
public interface PWM {
    void run(int value) throws SerialPortException, ModBusInvalidFunction;
}
