package jbus.modbus;

/**
 * Created by alexr on 10.02.2017.
 */
public class InvalidModBusFunction extends Exception{
    public InvalidModBusFunction() {
        super("Invalid ModBus Function (not supported by channel)");
    }
}
