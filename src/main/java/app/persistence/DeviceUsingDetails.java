package app.persistence;

import jbase.hex.HexFromByte;

/**
 * Created by mac on 30.07.2017.
 */
public class DeviceUsingDetails {
    private final int deviceid;
    private final int count;

    public DeviceUsingDetails(int deviceid, int count) {
        this.deviceid = deviceid;
        this.count = count;
    }

    public int id() {
        return deviceid;
    }

    public int count() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("Device id:%s, Chan count:%2d", new HexFromByte(deviceid), count);
    }
}
