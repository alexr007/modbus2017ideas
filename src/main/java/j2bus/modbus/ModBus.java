package j2bus.modbus;

import j2bus.comport.COMPortBaseInterface;
import jssc.SerialPortException;

import java.io.IOException;

/**
 * Created by alexr on 15.01.2017.
 */
public final class ModBus implements ModBusInterface{
    private final COMPortBaseInterface comPort;

    public ModBus(COMPortBaseInterface comPort) throws IOException {
        this.comPort=comPort;
        this.comPort.open();
    }

    @Override
    public synchronized MbResponse run(MbRequest req)  {
        MbResponse resp;
        try {
            resp = new MbResponse.Data(comPort.writeRead(req));
        } catch (IOException e) {
            resp = new MbResponse.Empty();
        }
        return resp;
    }

    @Override
    public void finish() throws IOException {
        comPort.close();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ModBus
            && this.comPort.equals(ModBus.class.cast(obj).comPort);
    }
}
