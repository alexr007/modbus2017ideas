package app.decision;

import jbus.modbus.response.Values;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by alexr on 24.04.2017.
 */
public class ValuesMap {
    private final HashMap<CharSequence, Values> source;

    public ValuesMap() {
        this(new HashMap<>());
    }

    public ValuesMap(HashMap<CharSequence, Values> source) {
        this.source = source;
    }

    public Values get(CharSequence k) throws Exception {
        if (!source.containsKey(k)) {
            throw new Exception(String.format("Requested entry:%s not exist in HashMap.",k));
        }
        return source.get(k);
    }

    public Values put(CharSequence k, Values v) throws Exception {
        if (source.containsKey(k)) {
            throw new Exception(String.format("Entry:%s already exist in HashMap.",k));
        }
        return source.put(k, v);
    }

    public Iterable<Map.Entry<CharSequence, Values>> entrySet() {
        return source.entrySet();
    }

    public Set<CharSequence> keySet() {
        return source.keySet();
    }

}
