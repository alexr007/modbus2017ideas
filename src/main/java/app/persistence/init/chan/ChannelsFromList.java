package app.persistence.init.chan;

import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import app.persistence.init.HashMapFrom;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;
import jwad.channels.WAD_Channel;

/**
 * Created by alexr on 27.04.2017.
 */
public class ChannelsFromList implements HashMapFrom<ChanName, WAD_Channel> {
    private final ModBusDevices devices;
    private final ArrayList<Pair<DevName, ChannelList>> channelsList;

    public ChannelsFromList(ModBusDevices devices, ArrayList<Pair<DevName, ChannelList>> channelsList) {
        this.devices = devices;
        this.channelsList = channelsList;
    }

    public HashMap<ChanName, WAD_Channel> hashMap() throws Exception {
        HashMap<ChanName, WAD_Channel> map = new HashMap<>();
        channelsList.forEach(
            (Pair<DevName, ChannelList> pair) -> {
                // iteration by each pair <dev, chan_list>
                // pair.getValue0() - device Name
                // pair.getValue1() - chanlist
                pair.getValue1().list().forEach(
                        (Pair<ChanName, Integer> chanItem) -> {
                            // iteration by each pair <chan_name, chan_id>
                            // chanItem.getValue0() - chan_name
                            // chanItem.getValue1() - chan_id
                            try {
                                map.put(
                                    chanItem.getValue0(),
                                    devices.get(pair.getValue0())
                                        .channel(chanItem.getValue1())
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    );
            }
        );
        return map;
    }
}
