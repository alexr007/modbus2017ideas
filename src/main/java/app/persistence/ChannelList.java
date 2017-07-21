package app.persistence;

import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mac on 21.07.2017.
 */
public class ChannelList {
    private final ArrayList<Pair<CharSequence, Integer>> items = new ArrayList<>();

    public ChannelList(Pair<CharSequence, Integer>... channels) {
        items.addAll(Arrays.asList(channels));
    }

    public ArrayList<Pair<CharSequence, Integer>> list() {
        return items;
    }
}
