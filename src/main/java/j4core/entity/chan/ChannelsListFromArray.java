package j4core.entity.chan;

import j4core.build.ChanEntry;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 21.07.2017.
 * used just to more verbose
 * array declaration
 */
public final class ChannelsListFromArray {
    private final List<ChanEntry> items;

    public ChannelsListFromArray(ChanEntry... entries) {
        this.items = Arrays.asList(entries);
    }

    public List<ChanEntry> list() {
        return items;
    }
}
