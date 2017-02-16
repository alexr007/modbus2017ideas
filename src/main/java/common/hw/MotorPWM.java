package common.hw;

import common.hw.port.AOPort;
import common.hw.port.DRPort;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
public class MotorPWM extends AbstractPerformer implements Switch,PWM
{
    protected final AOPort portValue;

    public MotorPWM(DRPort portEnable, AOPort portValue) {
        super(portEnable);
        this.portValue = portValue;
    }

    @Override
    public void run(int value) throws Exception {
        portValue.set(value);
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
