package j2bus.comport;

import j1base.primitives.Bytes;
import jssc.SerialPortException;

import java.io.IOException;

/**
 * Created by mac on 26.07.2017.
 */
public class COMPortFake implements COMPortBaseInterface {

    public COMPortFake(String portName) throws IOException {
        this(portName,
            new COMPortProperties(57600)
        );
    }

    public COMPortFake(String portName, COMPortProperties properties) throws IOException {
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public byte[] writeRead(byte[] buffer) throws IOException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public void cancelWrite() throws IOException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public void close() throws IOException {
    }
}
