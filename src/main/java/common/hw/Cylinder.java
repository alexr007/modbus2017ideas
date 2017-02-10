package common.hw;

import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 * Реализация гидроцилиндра
 * ========================
 *
 * для работы цилиндра надо два клапана:
 * один - на закрытие
 * один - на открытие
 *
 */
public class Cylinder {
    private final Valve valveToOpen;
    private final Valve valveToClose;

    public Cylinder(Valve valveToOpen, Valve valveToClose) {
        this.valveToOpen = valveToOpen;
        this.valveToClose = valveToClose;
    }

    public void open() throws SerialPortException, ModBusInvalidFunction {
        valveToClose.off();
        valveToOpen.on();
    }

    public void close() throws SerialPortException, ModBusInvalidFunction {
        valveToOpen.off();
        valveToClose.on();
    }

    public void stop() throws SerialPortException, ModBusInvalidFunction {
        valveToOpen.off();
        valveToClose.off();
    }
}
