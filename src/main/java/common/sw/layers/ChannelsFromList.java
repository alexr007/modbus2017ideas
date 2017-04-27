package common.sw.layers;

import common.hw.modbus.wad.WAD_Channel;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 27.04.2017.
 */
public class ChannelsFromList {
    private final ModBusDevices devices;
    private final ArrayList<Triplet<CharSequence, CharSequence, Integer>> channelsList;
    // idea based on custom Triplet type - currently lifeless
    // T = Triplet<CharSequence, CharSequence, Integer>
    //private final ArrayList<T> channelsList;
//    public ChannelsFromList(ModBusDevices devices, ArrayList<T> channels) {

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
