package app.persistence;

import jwad.channels.WAD_Channel;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 15.02.2017.
 */
public class ModBusChannels {
    private final ModBusDevices devices;
    private HashMap<CharSequence, WAD_Channel> channels = new HashMap<>();

    public ModBusChannels(ModBusDevices devices) throws Exception {
        this(devices, new ArrayList<>());
    }

    public ModBusChannels(ModBusDevices devices, ArrayList<Triplet<CharSequence, CharSequence, Integer>> channels) throws Exception {
        this.devices = devices;
        this.channels = new ChannelsFromList(devices, channels).hashMap();
    }

    public void add(CharSequence channelName, CharSequence deviceName, int channel) throws Exception {
        if (channels.containsKey(channelName)) {
            throw new Exception(String.format("Duplicate Channel Name:%s",channelName));
        }
        channels.put(channelName, devices.get(deviceName).channel(channel));
    }

    public WAD_Channel get(CharSequence channelName) throws Exception {
        if (!channels.containsKey(channelName)) {
            throw new Exception(String.format("Channel Name NotFound:%s",channelName));
        }
        return channels.get(channelName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Channels:\n");
        channels.forEach((k, v)->
            sb.append(String.format("CHAN: name:%s, type:%s, chan details:%s\n",
                k,
                v.type(),
                v.getClass().getSimpleName()))
        );
        sb.append("---\n");
        return sb.toString();
    }

    public void finish() throws SerialPortException {
        devices.finish();
    }
}
