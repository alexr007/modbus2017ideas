package common.sw.decision;

import common.hw.modbus.response.Values;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by alexr on 24.04.2017.
 */
public class ValuesMap {
    private final HashMap<CharSequence, Values> source;

    public ValuesMap(HashMap<CharSequence, Values> source) {
        this.source = source;
    }

    public Values get(CharSequence k) throws Exception {
        if (!source.containsKey(k)) {
            throw new Exception(String.format("Requested entry:%s not exist in HashMap.",k));
        }
        return source.get(k);
    }

    public Iterable<Map.Entry<CharSequence, Values>> entrySet() {
        return source.entrySet();
    }

    public Set<CharSequence> keySet() {
        return source.keySet();
    }

}
