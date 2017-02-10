package common.hw;

import common.hw.port.DRPort;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 * Реализация клапана
 * ========================
 *
 * клапан может быть:
 * - открыт
 * - зкрыт
 *
 */
public class Valve extends AbstractPerformer implements Switch {

    public Valve(DRPort port) {
        super(port);
    }

    public void on() throws SerialPortException, ModBusInvalidFunction {
        port.on();
    }

    public void off() throws SerialPortException, ModBusInvalidFunction {
        port.off();
    }

}
