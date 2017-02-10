package common.hw.modbus.response;

/**
 * Created by alexr on 22.01.2017.
 */
public class RqInfo {
    private final int deviceId;
    private final int command;
    private final int length;

    public RqInfo(int deviceId, int command, int size) {
        this.deviceId = deviceId;
        this.command = command;
        this.length = size;
    }

    public int deviceId() {
        return deviceId;
    }

    public int command() {
        return command;
    }

    public int length() {
        return length;
    }
}
