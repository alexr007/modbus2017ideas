package app.persistence.init.chan;

import app.persistence.ChannelList;
import app.persistence.init.HashMapFrom;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;
import jbus.modbus.InvalidModBusFunction;
import jwad.channels.WAD_Channel;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by alexr on 15.02.2017.
 */
public class ModBusChannels {
    private final ModBusDevices devices;
    private final HashMap<ChanName, WAD_Channel> channels;

    // ctor 1 - empty list
    public ModBusChannels(ModBusDevices devices) throws Exception {
        this(devices, new ArrayList<>());
    }

    // ctor 2 - random quantity Pair<WadAbstractDevice, ChannelList>
    public ModBusChannels (ModBusDevices devices, Pair<WadAbstractDevice, ChannelList>... channels) throws Exception {
        this(devices, new ArrayList<>(Arrays.asList(channels)));
    }

    // ctor 3 - ArrayList<Pair<WadAbstractDevice, ChannelList>>
    public ModBusChannels (ModBusDevices devices, ArrayList<Pair<WadAbstractDevice, ChannelList>> channels) throws Exception {
        this(devices, new ChannelsFromList(devices, channels));
    }

    // ctor 4 - HashMapFrom
    public ModBusChannels(ModBusDevices devices, HashMapFrom<ChanName, WAD_Channel> channels) throws Exception {
        this(devices, channels.hashMap());
    }

    // ctor 5 - plain assignment
    public ModBusChannels(ModBusDevices devices, HashMap<ChanName, WAD_Channel> channels) {
        this.devices = devices;
        this.channels = channels;
    }

    // =================================================================================================================

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
