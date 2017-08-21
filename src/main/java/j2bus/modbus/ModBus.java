package j2bus.modbus;

import j2bus.comport.COMPortBaseInterface;
import jssc.SerialPortException;

import java.io.IOException;

/**
 * Created by alexr on 15.01.2017.
 */
public class ModBus {
    private final COMPortBaseInterface comPort;

    public ModBus(COMPortBaseInterface comPort) {
        this.comPort = comPort;
    }

    private void close() throws IOException {
        this.comPort.close();
    }

    public synchronized MbResponse run(MbRequest req)  {
        return doRequest(req);
    }

    private MbResponse doRequest(MbRequest req)  {
        MbResponse response;
        try {
            response = new MbResponse.Data(
                comPort.writeRead(req.bytes())
            );
        } catch (IOException e) {
            response = new MbResponse.Empty();
        }
        return response;
    }

    public void finish() throws IOException {
       this.close();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ModBus
            && this.comPort.equals(ModBus.class.cast(obj).comPort);
    }
}
