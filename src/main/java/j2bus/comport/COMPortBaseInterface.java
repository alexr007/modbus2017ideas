package j2bus.comport;

import java.io.IOException;

/**
 * Created by mac on 19.06.2017.
 */
public interface COMPortBaseInterface {
    void write(byte[] buffer) throws IOException;
    byte[] writeRead(byte[] buffer) throws IOException;
    void open() throws IOException;
    void close() throws IOException;
}
