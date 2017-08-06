package app.persistence.init.chan;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import app.persistence.init.EnumMapFrom;
import jwad.channels.WAD_Channel;
import org.javatuples.Pair;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;

/**
 * Created by alexr on 27.04.2017.
 */
public class ChannelsFromList implements EnumMapFrom<ChanName, WAD_Channel> {
    private final ModBusDevices devices;
    private final ArrayList<Pair<DevName, ChannelList>> channelsList;

    public ChannelsFromList(ModBusDevices devices, ArrayList<Pair<DevName, ChannelList>> channelsList) {
        this.devices = devices;
        this.channelsList = channelsList;
    }

    public EnumMap<ChanName, WAD_Channel> enumMap() throws Exception {
        EnumMap<ChanName, WAD_Channel> map = new EnumMap<>(ChanName.class);
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
                        map.put(
                            chanItem.getValue0(),
                            devices.get(pair.getValue0())
                                .channel(chanItem.getValue1())
                        );
                    }
                );
            }
        );
        return map;
    }
}
