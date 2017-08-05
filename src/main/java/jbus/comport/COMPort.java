package jbus.comport;

import jbase.primitives.Bytes;
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

    /**
     *
     * @param portName
     * @throws SerialPortException
     */
    public COMPort(String portName) throws SerialPortException {
        this(portName,
            new COMPortProperties(57600)
        );
    }

    /**
     *
     * @param portName
     * @param properties
     * @throws SerialPortException
     */
    public COMPort(String portName, COMPortProperties properties) throws SerialPortException {
        this.port = new SerialPort(portName);
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
    }

    /**
     * We need the close port after use it
     */
    @Override
    public void close() throws SerialPortException {
        port.closePort();
    }

    @Override
    public void write(Bytes buffer) throws SerialPortException {
        port.writeBytes(buffer.bytes());
    }

    /*
     * just write byte[] to buffer
     */
    @Override
    public void write(byte[] buffer) throws SerialPortException {
        port.writeBytes(buffer);
    }

    /**
     * write byte[] to buffer
     * and wait byte[] from buffer
     */
    @Override
    public byte[] writeRead(byte[] buffer) throws SerialPortException, InterruptedException {
        synchronized (locker) {
            received = false;
            port.writeBytes(buffer);
            while (!received) {
                locker.wait();
            }
            return readed;
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
     * Inner class to implement read from serial DRPort
     * in different thread
     *
     */

    class Listener implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent event) {
            synchronized (locker) {
                if (event.isRXCHAR() & event.getEventValue()>0) {
                    try {
                        // readed = port.readBytes(event.getEventValue(),100);
                        // System.currentTimeMillis - startTime < timeout)
                        readed = port.readBytes(event.getEventValue());
                        received = true;
                    } catch (SerialPortException e1) {
                        System.out.println("COM port error");
                        e1.printStackTrace();
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
