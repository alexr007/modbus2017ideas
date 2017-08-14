package j2bus.comport;

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
        this(SerialPort.BAUDRATE_57600,
            SerialPort.DATABITS_8,
            SerialPort.PARITY_NONE,
            SerialPort.STOPBITS_1
        );
    }

    public COMPortProperties(int baudRate) {
        this(baudRate,
            SerialPort.DATABITS_8,
            SerialPort.PARITY_NONE,
            SerialPort.STOPBITS_1
            );
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
