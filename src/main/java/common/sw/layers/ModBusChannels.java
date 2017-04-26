package common.sw.layers;

import common.hw.modbus.wad.WAD_Channel;

import java.util.HashMap;

/**
 * Created by alexr on 15.02.2017.
 */
public class ModBusChannels {
    private final ModBusDevices devices;
    private final HashMap<String, WAD_Channel> channels = new HashMap<>();

    public ModBusChannels(ModBusDevices devices) {
        this.devices = devices;
    }

    public void add(String channelName, String deviceName, int channel) throws Exception {
        if (channels.containsKey(channelName)) {
            throw new Exception(String.format("Duplicate Channel Name:%s",channelName));
        }
        channels.put(channelName, devices.get(deviceName).channel(channel));
    }

    public WAD_Channel get(String channelName) throws Exception {
        if (!channels.containsKey(channelName)) {
            throw new Exception(String.format("Channel Name NotFound:%s",channelName));
        }
        return channels.get(channelName);
    }
}
