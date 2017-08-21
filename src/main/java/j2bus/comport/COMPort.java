package j2bus.comport;

import j1base.primitives.Bytes;
import jssc.*;

import java.io.IOException;

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
    // locker for multithreading
    private final Object locker = new Object();

    public COMPort(String portName) throws IOException {
        this(portName,
            new COMPortProperties(57600)
        );
    }

    public COMPort(String portName, COMPortProperties properties) throws IOException {
        this.port = new SerialPort(portName);
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
            throw new IOException("COM Port. can't open",e);
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
            throw new IOException("COM Port. can't write",e);
        }
    }

    @Override
    public byte[] writeRead(byte[] buffer) throws IOException {
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
     * Inner class to implement read from serial port
     * in different thread
     */
    class Listener implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event) {
            synchronized (locker) {
                if (event.isRXCHAR() & event.getEventValue()>0) {
                    try {
                        readed = port.readBytes(event.getEventValue());
                        received = true;
                    } catch (SerialPortException ex) {
                        throw new IllegalArgumentException("COM port error", ex);
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
