package app.persistence.init.chan;

import java.util.EnumMap;
import java.util.List;

import app.persistence.init.MapFrom;
import jwad.channels.WAD_Channel;
import org.javatuples.Pair;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;

/**
 * Created by alexr on 27.04.2017.
 */
public class ChannelsFromList implements MapFrom<ChanName, WAD_Channel> {
    private final ModBusDevices devices;
    private final List<Pair<DevName, ChannelList>> channelsList;

    public ChannelsFromList(ModBusDevices devices, List<Pair<DevName, ChannelList>> channelsList) {
        this.devices = devices;
        this.channelsList = channelsList;
    }

    public EnumMap<ChanName, WAD_Channel> map() throws Exception {
        EnumMap<ChanName, WAD_Channel> map = new EnumMap<>(ChanName.class);
        channelsList.forEach(
            (Pair<DevName, ChannelList> pairDevChanList) -> {
                // iteration by each pairDevChanList <dev, chan_list>
                // pairDevChanList.getValue0() - device Name
                // pairDevChanList.getValue1() - chanlist
                pairDevChanList.getValue1().list().forEach(
                    (ent) -> {
                        // iteration by each pairDevChanList <chan_name, chan_id>
                        // ent.getValue0() - chan_name
                        // ent.getValue1() - chan_id
                        map.put(
                            ent.name(),
                            devices.get(pairDevChanList.getValue0()).channel(ent.chanId())
                        );
                    }
                );
            }
        );
        return map;
    }
}
