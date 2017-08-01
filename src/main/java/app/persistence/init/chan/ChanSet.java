package app.persistence.init.chan;

import constants.ChanName;
import jwad.channels.WAD_Channel;
import java.util.EnumMap;
import java.util.Set;

public class ChanSet {
    private final EnumMap<ChanName, WAD_Channel> channelMap;
    private final Set<ChanName> origin;

    public ChanSet(EnumMap<ChanName, WAD_Channel> channelMap, Set<ChanName> origin) {
        this.channelMap = channelMap;
        this.origin = origin;
    }

}
