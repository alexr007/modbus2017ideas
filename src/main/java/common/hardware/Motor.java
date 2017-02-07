package common.hardware;

import common.hardware.port.DRPort;
import common.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
public class Motor extends AbstractPerformer implements Switch {

    public Motor(DRPort port) {
        super(port);
    }

    @Override
    public void on() throws SerialPortException, ModBusInvalidFunction {
        port.on();
    }

    @Override
    public void off() throws SerialPortException, ModBusInvalidFunction {
        port.off();
    }
}
