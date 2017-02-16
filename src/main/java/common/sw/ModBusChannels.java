package common.sw;

import common.hw.modbus.wad.WAD_Channel;

import java.util.HashMap;

/**
 * Created by alexr on 15.02.2017.
 */
public class ModBusChannels {
    private final ModBusDevices devices;
    private HashMap<String, WAD_Channel> channels = new HashMap<>();

    public ModBusChannels(ModBusDevices devices) {
        this.devices = devices;
    }

    public void add(String channelName, String deviceName, int channel) throws Exception {
        if (channels.containsKey(channelName)) {
            throw new Exception("Duplicate Channel Name:"+channelName);
        }
        channels.put(channelName, devices.get(deviceName).channel(channel));
    }

    public WAD_Channel get(String channelName) throws Exception {
        if (!channels.containsKey(channelName)) {
            throw new Exception("Channel Name NotFound:"+channelName);
        }
        return channels.get(channelName);
    }
}
