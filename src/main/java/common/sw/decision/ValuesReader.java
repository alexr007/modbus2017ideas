package common.sw.decision;

import common.hw.modbus.response.Values;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by alexr on 24.04.2017.
 */
public class ValuesReader implements Iterable {
    private final Iterable<CharSequence> source;
    private final PortReader reader;

    public ValuesReader(Iterable<CharSequence> src, PortReader reader) {
        this.source = src;
        this.reader = reader;
    }

    @Override
    public Iterator iterator() {
        return source.iterator();
    }

    HashMap<CharSequence, Values> get() {
        HashMap<CharSequence, Values> values = new HashMap<>();
        for (CharSequence name : source) {
            values.put(name, reader.get(name));
        }
        return values;
    }

}
