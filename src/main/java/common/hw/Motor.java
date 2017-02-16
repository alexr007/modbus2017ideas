package common.hw;

import common.hw.port.DRPort;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
public class Motor extends AbstractPerformer implements Switch {

    public Motor(DRPort port) {
        super(port);
    }

    @Override
    public void on() throws Exception {
        port.on();
    }

    @Override
    public void off() throws Exception {
        port.off();
    }
}
