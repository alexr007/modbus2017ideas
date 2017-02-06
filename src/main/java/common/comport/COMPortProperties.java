package common.comport;

import jssc.SerialPort;

/**
 * Created by alexr on 20.01.2017.
 */
public class COMPortProperties {
    private final int baudRate;
    private final int dataBits;
    private final int parity;
    private final int stopBits;

    public COMPortProperties() {
        this.baudRate = SerialPort.BAUDRATE_57600;
        this.dataBits = SerialPort.DATABITS_8;
        this.parity = SerialPort.PARITY_NONE;
        this.stopBits = SerialPort.STOPBITS_1;
    }

    public COMPortProperties(int baudRate) {
        this.baudRate = baudRate;
        this.dataBits = SerialPort.DATABITS_8;
        this.parity = SerialPort.PARITY_NONE;
        this.stopBits = SerialPort.STOPBITS_1;
    }

    public COMPortProperties(int baudRate, int dataBits, int parity, int stopBits) {
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.parity = parity;
        this.stopBits = stopBits;
    }

    public int baudRate() {
        return baudRate;
    }

    public int dataBits() {
        return dataBits;
    }

    public int parity() {
        return parity;
    }

    public int stopBits() {
        return stopBits;
    }
}
