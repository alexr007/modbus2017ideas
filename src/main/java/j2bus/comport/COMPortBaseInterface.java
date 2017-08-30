package j2bus.comport;

import j1base.primitives.Bytes;

import java.io.IOException;

/**
 * Created by mac on 19.06.2017.
 */
public interface COMPortBaseInterface {
    void write(byte[] buffer) throws IOException;
    default byte[] writeRead(Bytes buffer) throws IOException {
        return writeRead(buffer.bytes());
    }
    byte[] writeRead(byte[] buffer) throws IOException;
    void open() throws IOException;
    void close() throws IOException;
}
