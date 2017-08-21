package j2bus.comport;

import com.jcabi.aspects.Timeable;
import j1base.primitives.Bytes;
import jssc.SerialPortException;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * Created by mac on 19.06.2017.
 */
public class COMPortTimed implements COMPortBaseInterface {
    private final COMPortBaseInterface origin;
    private final long timeout;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public COMPortTimed(COMPortBaseInterface origin, long timeout) {
        this.origin = origin;
        this.timeout = timeout;
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                try {
                    origin.write(buffer);
                } catch (IOException e) {
                    throw new IllegalArgumentException("COM Port: Timeout", e);
                }
            }
        };
        executor.scheduleWithFixedDelay(writer,0, this.timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    @Timeable (limit = 12, unit = TimeUnit.MILLISECONDS)
    public byte[] writeRead(byte[] buffer) throws IOException {
        return origin.writeRead(buffer);
    }

    @Override
    public void cancelWrite() throws IOException {
        origin.cancelWrite();
    }

    @Override
    public void close() throws IOException {
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                try {
                    origin.close();
                } catch (IOException e) {
                    throw new IllegalArgumentException("COM Port: Timeout", e);
                }
            }
        };
        executor.scheduleWithFixedDelay(writer,0, this.timeout, TimeUnit.MILLISECONDS);
    }
}