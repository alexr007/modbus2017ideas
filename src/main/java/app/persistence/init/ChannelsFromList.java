package app.persistence.init;

import app.persistence.ChannelList;
import app.persistence.init.ModBusDevices;
import jwad.channels.WAD_Channel;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by alexr on 27.04.2017.
 */
public class ChannelsFromList {
    private final ModBusDevices devices;
    private final ArrayList<Triplet<CharSequence, CharSequence, Integer>> channelsList = new ArrayList<>();
    private final ArrayList<Triplet<CharSequence, WadAbstractDevice, Integer>> channelsList2 = new ArrayList<>();

    /**
     *
     * @param devices
     * @param channels - ArrayList<Triplet>
     *                 1st: CharSequence - Channel name;
     *                 2nd: CharSequence - Device name (Channel owner)
     *                 3rd: Integer      - Channel Id
     */
    public ChannelsFromList(ModBusDevices devices, ArrayList<Triplet<CharSequence, CharSequence, Integer>> channels) {
        this.devices = devices;
        this.channelsList.addAll(channels);
    }

    public ChannelsFromList(ModBusDevices devices, Pair<WadAbstractDevice, ChannelList>... channels) {
        this.devices = devices;
        for (Pair<WadAbstractDevice, ChannelList> channel : channels) {
            for (Pair<CharSequence, Integer> item : channel.getValue1().list()) {
                this.channelsList2.add(new Triplet<>(
                    item.getValue0(), // channel name
                    channel.getValue0(), // device name
                    item.getValue1() // channel id
                ));
            }
        }
    }

    public HashMap<CharSequence,WAD_Channel> hashMap() throws Exception {
        HashMap<CharSequence, WAD_Channel> map = new HashMap<>();
        for (Triplet<CharSequence, WadAbstractDevice, Integer> item : channelsList2) {
            if (map.containsKey(item.getValue0())) {
                throw new Exception(String.format("Duplicate Channels name: %s", item.getValue0()));
            }
            map.put(
                item.getValue0(),
                item.getValue1()
                    .channel(item.getValue2())

/*
                devices.get(item.getValue1())
*/
            );
        }
        return map;
    }
}
