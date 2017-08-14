package app.persistence.init.chan;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 21.07.2017.
 * used just to more verbose
 * array declaration
 */
public class ChannelList {
    private List<ChanEntry> items;

    public ChannelList(ChanEntry... entries) {
        this.items = Arrays.asList(entries);
    }

    public List<ChanEntry> list() {
        return items;
    }
}
