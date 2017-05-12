package jbus.modbus;

import jbus.comport.COMPort;
import jssc.SerialPortException;

/**
 * Created by alexr on 15.01.2017.
 */
public class ModBus {
    private final COMPort comPort;

    public ModBus(COMPort comPort) {
        this.comPort = comPort;
    }

    private void close() throws SerialPortException {
        this.comPort.close();
    }

    public synchronized MbResponse run(MbRequest req) throws SerialPortException {
        return doRequest(req);
    }

    private MbResponse doRequest(MbRequest req) throws SerialPortException {
        MbResponse response;
        try {
            response = new MbResponse.Data(
                comPort.writeRead(req.get())
            );
        }
        catch (InterruptedException ex)
        {
            response = new MbResponse.Empty();
        }
        return response;
    }

    public void finish() throws SerialPortException {
       this.close();
    }
}
