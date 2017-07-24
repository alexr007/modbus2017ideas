package app.persistence;

import constants.ChanName;
import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mac on 21.07.2017.
 */
public class ChannelList {
    private final ArrayList<Pair<ChanName, Integer>> items = new ArrayList<>();

    public ChannelList(Pair<ChanName, Integer>... channels) {
        items.addAll(Arrays.asList(channels));
    }

    public ArrayList<Pair<ChanName, Integer>> list() {
        return items;
    }
}
