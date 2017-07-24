package app.persistence.init;

import app.persistence.ChannelList;
import constants.DevName;
import jbus.modbus.InvalidModBusFunction;
import jwad.channels.WAD_Channel;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 15.02.2017.
 */
public class ModBusChannels {
    private final ModBusDevices devices;
    private HashMap<CharSequence, WAD_Channel> channels;

    public ModBusChannels(ModBusDevices devices) throws Exception {
        this(devices, new ArrayList<>());
    }

    // add channels by CHANNEL_NAME, DEVICE_NAME, CHANNEL_ID
    public ModBusChannels(ModBusDevices devices, ArrayList<Triplet<CharSequence, CharSequence, Integer>> channels) throws Exception {
        this.devices = devices;
        this.channels = new ChannelsFromList(devices, channels).hashMap();
    }

    // add channels by list of Pair<DEVICE, Pair<CHANNEL_NAME, CHANNEL_ID>>
    public ModBusChannels (ModBusDevices devices, Pair<WadAbstractDevice, ChannelList>... channels) throws Exception {
        this.devices = devices;
        this.channels = new ChannelsFromList(devices, channels).hashMap();
    }

    public void add(CharSequence channelName, DevName deviceName, int channel) throws Exception {
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

    /**
     * channels<key,chan>
     *     key - channel name during initialization
     *     chan - channel
     *     chan.device() - device
     *     chan.device().name() - device name (WAD_AIK_BUS, WAD_AO_BUS, WAD_DOS_BUS)
     *     chan.device().type() - device type (AIK, AO, DOS)
     *     chan.device().id() - device modbus id
     *     chan.device().properties() - device properties
     *     chan.device().properties().portType()
     *     chan.device().properties().signalType()
     *     chan.device().properties().chanCount()
     *
     */
    public ArrayList<Triplet> triplet() {
        ArrayList<Triplet> list = new ArrayList<>();
        channels.forEach((key, chan) -> {
            try {
                list.add(new Triplet<CharSequence, CharSequence, Integer>(
                    //key, chan.device().name(), chan.channel()
                    key, chan.device().name(), chan.device().id()
                    )
                );
            } catch (InvalidModBusFunction invalidModBusFunction) {
                invalidModBusFunction.printStackTrace();
            }
        });
        return list;
    }
}
