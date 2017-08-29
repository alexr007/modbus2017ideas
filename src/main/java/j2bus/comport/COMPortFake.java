package j2bus.comport;

import java.io.IOException;

/**
 * Created by mac on 26.07.2017.
 */
public class COMPortFake implements COMPortBaseInterface {

    public COMPortFake(String portName, long timeout) throws IOException {
        this(portName,
            new COMPortProperties(57600),
            timeout
        );
    }

    public COMPortFake(String portName, COMPortProperties properties, long timeout) throws IOException {
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
    public void open() throws IOException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

    @Override
    public void close() throws IOException {
        throw new IllegalArgumentException("Serial port in fake mode (for testing purposes)");
    }

}
