package j2bus.comport;

import j1base.primitives.Bytes;
import jssc.SerialPortException;

import java.io.IOException;

/**
 * Created by mac on 19.06.2017.
 */
public interface COMPortBaseInterface {
    void write(byte[] buffer) throws SerialPortException;
    void write(Bytes buffer) throws SerialPortException;
    byte[] writeRead(byte[] buffer) throws SerialPortException, InterruptedException;
    void cancelWrite() throws IOException;
    void close() throws SerialPortException;
}
