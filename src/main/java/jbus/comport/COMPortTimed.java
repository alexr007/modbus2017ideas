package jbus.comport;

import jbase.primitives.Bytes;
import jssc.SerialPortException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mac on 19.06.2017.
 */
public class COMPortTimed implements COMPortBaseInterface {
    private final COMPortBaseInterface origin;
    private final long timeout;
    ExecutorService executor = Executors.newSingleThreadExecutor();


    public COMPortTimed(COMPortBaseInterface origin, long timeout) {
        this.origin = origin;
        this.timeout = timeout;
    }

    @Override
    public void write(byte[] buffer) throws SerialPortException {
        throw new IllegalArgumentException ("COMPortTimed.write(byte[] buffer) not implemented\n");
    }

    @Override
    public void write(Bytes buffer) throws SerialPortException {
        throw new IllegalArgumentException ("COMPortTimed.write(Bytes buffer) not implemented\n");
    }

    @Override
    public synchronized byte[] writeRead(byte[] buffer) throws SerialPortException, InterruptedException {
        return new byte[0];
    }

    @Override
    public void cancelWrite() throws IOException {
        origin.cancelWrite();
    }

    @Override
    public void close() throws SerialPortException {
        origin.close();
    }
}
