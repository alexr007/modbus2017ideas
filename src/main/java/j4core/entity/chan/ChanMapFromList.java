package j4core.entity.chan;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import j4core.entity.dev.ModBusDevices;
import j4core.entity.MapFrom;
import j3wad.channels.WAD_Channel;
import org.javatuples.Pair;
import constants.ChanName;
import constants.DevName;

/**
 * Created by alexr on 27.04.2017.
 */
public final class ChanMapFromList implements MapFrom<ChanName, WAD_Channel> {
    private final ModBusDevices devices;
    private final List<Pair<DevName, ChannelsListFromArray>> channelsList;

    public ChanMapFromList(ModBusDevices devices, List<Pair<DevName, ChannelsListFromArray>> channelsList) {
        this.devices = devices;
        this.channelsList = channelsList;
    }

    public Map<ChanName, WAD_Channel> map() {
        Map<ChanName, WAD_Channel> map = new EnumMap<>(ChanName.class);
        channelsList.forEach(
            (Pair<DevName, ChannelsListFromArray> pairDevChanList) -> {
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
