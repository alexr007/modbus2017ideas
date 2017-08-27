package j2bus.comport;

import jssc.*;
import java.io.IOException;

import static java.lang.Math.toIntExact;

/**
 * Created by alexr on 02.12.2016.
 */
public class COMPort implements COMPortBaseInterface {
    // SerialPort instance, that wrapped
    private final SerialPort port;
    // listener for comport read
    private final Listener listener = new Listener();
    // bytes received ready to exit from thread.
    private boolean received;
    // readed bytes
    private byte[] readed;
    // locker for multithreaded
    private final Object locker = new Object();
    // timeout, milliseconds
    private final long timeout;
    // executor for timeout

    public COMPort(String portName) throws IOException {
        this(portName, 1_000L); // default timeout = 1 second
    }

    public COMPort(String portName, long timeout) throws IOException {
        this(portName,
            new COMPortProperties(57600),
            timeout); // default timeout
    }

    public COMPort(String portName, COMPortProperties properties, long timeout) throws IOException {
        this.port = new SerialPort(portName);
        this.timeout = timeout;
        try {
            port.openPort();
            port.setParams(
                properties.baudRate(),
                properties.dataBits(),
                properties.stopBits(),
                properties.parity()
            );
            port.setFlowControlMode(
                SerialPort.FLOWCONTROL_NONE
                //| SerialPort.FLOWCONTROL_RTSCTS_IN
                //| SerialPort.FLOWCONTROL_RTSCTS_OUT
            );
            this.port.addEventListener(listener, SerialPort.MASK_RXCHAR);
        } catch (SerialPortException e) {
            throw new IOException("COM Port. can't open @ CTOR",e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            port.closePort();
        } catch (SerialPortException e) {
            throw new IOException("COM Port. can't close",e);
        }
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        try {
            port.writeBytes(buffer);
        } catch (SerialPortException e) {
            throw new IOException("COM Port. Write error");
        }
    }

    private byte[] waitAndRead() throws InterruptedException, IOException {
        final long start = System.nanoTime();
        while (!received) {
            locker.wait(timeout);
            if ( System.nanoTime()-start > timeout*1_000_000) {
                throw new IOException("COM Port. TimeoutException");
            }
        }
        return readed;
    }

    @Override
    public byte[] writeRead(byte[] buffer) throws IOException {
        try {
            synchronized (locker) {
                received = false;
                this.write(buffer);
                return waitAndRead();
            }
        } catch (InterruptedException e) {
            throw new IOException("COM Port. InterruptedException",e);
        }
    }

    @Override
    public void cancelWrite() throws IOException {
        try {
            port.purgePort(SerialPort.PURGE_TXABORT);
            port.purgePort(SerialPort.PURGE_TXCLEAR);
        } catch (SerialPortException e) {
            throw new IOException(e);
        }
    }

    /**
     * Inner class to implement read from serial port in different thread
     */
    class Listener implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event) {
            synchronized (locker) {
                if (event.isRXCHAR() & event.getEventValue()>0) {
                    try {
                        readed = port.readBytes(event.getEventValue(), toIntExact(timeout));
                    } catch (SerialPortTimeoutException| SerialPortException ex) {
                        readed = new byte[0];
                    } finally {
                        received = true;
                    }
                }
                locker.notify();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof COMPort
            && this.port.equals(COMPort.class.cast(obj).port);
    }

}
