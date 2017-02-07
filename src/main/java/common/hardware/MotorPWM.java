package common.hardware;

import common.hardware.port.AOPort;
import common.hardware.port.DRPort;
import common.modbus.wad.ModBusInvalidFunction;
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
    public void run(int value) throws SerialPortException, ModBusInvalidFunction {
        portValue.run(value);
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
