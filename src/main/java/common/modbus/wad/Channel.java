package common.modbus.wad;

import common.modbus.response.InvalidModBusResponse;
import common.modbus.response.InvalidModBusResponseCRC;
import common.modbus.response.InvalidModBusResponseLength;
import common.modbus.response.Values;
import jssc.SerialPortException;

/**
 * Created by alexr on 21.01.2017.
 */
public interface Channel {
    // AI, DI, DO, DOS
    // Values.Single
    // Values.Multiple
    public Values get() throws SerialPortException, InvalidModBusResponse, ModBusInvalidFunction;
    // DI, AI
    public Values fail() throws ModBusInvalidFunction, InvalidModBusResponse, SerialPortException;
    // DI, DOS
    // TODO doubtful if channel==0
    public boolean opened() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
    public boolean closed() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
    // DOS
    public void on() throws SerialPortException, ModBusInvalidFunction;
    public void off() throws SerialPortException, ModBusInvalidFunction;
    // incredible idea )
    //public void on(int channels) throws SerialPortException;
    //public void on(int[] channels) throws SerialPortException;
    //public void off(int channels) throws SerialPortException;
    //public void off(int[] channels) throws SerialPortException;
    // AO, DOS
    public void set(int val) throws SerialPortException, ModBusInvalidFunction;
    public void set(int[] val) throws SerialPortException, ModBusInvalidFunction;
}
