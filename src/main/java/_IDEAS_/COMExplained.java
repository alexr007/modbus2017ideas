package _IDEAS_;

import j2bus.comport.COMPortBaseInterface;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;
import java.util.concurrent.*;

public class COMExplained {
    private byte[] readed;
    private final Object locker = new Object();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final long timeout;
    private final SerialPort port;
    private boolean received;

    public COMExplained(SerialPort port, long timeout) {
        this.port = port;
        this.timeout = timeout;
    }

    void close() {
        executorService.shutdown();
    }

    // inner class to implement write with timeout
    private static class BackgroundWriter implements Callable<Boolean> {
        private final SerialPort port;
        private final byte[] toWrite;

        BackgroundWriter(SerialPort port, byte[] toWrite) {
            this.port = port;
            this.toWrite = toWrite;
        }

        @Override
        public Boolean call() throws Exception {
            port.writeBytes(toWrite);
            return true;
        }
    }

    public void write(byte[] buffer) throws IOException {
        try {
            executorService.submit(new BackgroundWriter(port, buffer))
                .get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (port.isOpened()) {
                try {
                    port.closePort();
                } catch (SerialPortException e1) {
                    throw new IOException("COM Port, Can't close after Timeout.", e1);
                }
            }
            throw new IOException("COM Port, Can't write. Timeout.", e);
        }
    }

    public byte[] writeRead_OK_Worked_fine(byte[] buffer) throws IOException {
        try {
            synchronized (locker) {
                received = false;
                port.writeBytes(buffer);
                while (!received) {
                    locker.wait();
                }
                return readed;
            }
        } catch (SerialPortException e) {
            throw new IOException("COM Port. can't write",e);
        } catch (InterruptedException e) {
            throw new IOException("COM Port. InterruptedException",e);
        }
    }

}
