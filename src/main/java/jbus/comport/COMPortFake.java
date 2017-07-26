package jbus.comport;

import jbase.primitives.Bytes;
import jssc.SerialPortException;

import java.io.IOException;

/**
 * Created by mac on 26.07.2017.
 */
public class COMPortFake implements SimpleSerialInterface {

    public COMPortFake(String portName) throws SerialPortException {
        this(portName,
            new COMPortProperties(57600)
        );
    }

    public COMPortFake(String portName, COMPortProperties properties) throws SerialPortException {
    }

    @Override
    public void write(byte[] buffer) throws SerialPortException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public void write(Bytes buffer) throws SerialPortException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public byte[] writeRead(byte[] buffer) throws SerialPortException, InterruptedException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public void cancelWrite() throws IOException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public void close() throws SerialPortException {
    }
}
