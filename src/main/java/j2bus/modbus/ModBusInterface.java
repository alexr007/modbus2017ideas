package j2bus.modbus;

import java.io.IOException;

public interface ModBusInterface {
    MbResponse run(MbRequest req);
    void finish() throws IOException;
}
