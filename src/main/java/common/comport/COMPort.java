package common.comport;

import common.primitives.Bytes;
import jssc.*;

/**
 * Created by alexr on 02.12.2016.
 */
public class COMPort {
    // SerialPort instance, that wrapped
    private final SerialPort port;
    // listener for comport read
    private final Listener listener = new Listener();
    // get received ready to exit from thread.
    private boolean received;
    // readed get
    private byte[] readed;
    // locker for multithreading
    private Object locker = new Object();

    /*
     * Ctor with COMPortProperties
     *
     */
    public COMPort(String portName, COMPortProperties properties) throws SerialPortException {
        port = new SerialPort(portName);
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

    /*
     * Ctor with Default Properties 57600,8,N,1
     *
     */
    public COMPort(String portName) throws SerialPortException {
        this(
            portName,
            new COMPortProperties(57600)
        );
    }
    /*
     * You need the close port after use it
     *
     */
    public void close() throws SerialPortException {
        port.closePort();
    }

    public void write(Bytes buffer) throws SerialPortException {
        port.writeBytes(buffer.get());
    }

    /*
     * just write byte[] to buffer
     */
    public void write(byte[] buffer) throws SerialPortException {
        port.writeBytes(buffer);
    }

    /*
     * write byte[] to buffer
     * and wait byte[] from buffer
     *
     */
    public byte[] writeRead(byte[] buffer) throws SerialPortException, InterruptedException {
        synchronized (locker) {
            received = false;
            port.writeBytes(buffer);
            while (!received) locker.wait();
            return readed;
        }
    }

    /*
     * Inner class to implement read from serial Port
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


}
