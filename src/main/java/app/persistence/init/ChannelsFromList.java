package app.persistence.init;

import app.persistence.init.ModBusDevices;
import jwad.channels.WAD_Channel;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 27.04.2017.
 */
public class ChannelsFromList {
    private final ModBusDevices devices;
    private final ArrayList<Triplet<CharSequence, CharSequence, Integer>> channelsList;

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
        this.channelsList = channels;
    }

    public HashMap<CharSequence,WAD_Channel> hashMap() throws Exception {
        HashMap<CharSequence, WAD_Channel> map = new HashMap<>();
        for (Triplet<CharSequence, CharSequence, Integer> item : channelsList) {
            if (map.containsKey(item.getValue0())) {
                throw new Exception(String.format("Duplicate Channels name: %s", item.getValue0()));
            }
            map.put(
                item.getValue0(),
                devices.get(item.getValue1())
                    .channel(item.getValue2())
            );
        }
        return map;
    }
}
