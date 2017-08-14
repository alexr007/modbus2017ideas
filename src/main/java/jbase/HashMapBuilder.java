package jbase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashMapBuilder<K,V> {
    private final List<Map.Entry<K, ? extends V>> origin;

    public HashMapBuilder(Map.Entry<K,? extends V>... origin) {
        this(Arrays.asList(origin));
    }

    public HashMapBuilder(List<Map.Entry<K,? extends V>> origin) {
        this.origin = origin;
    }

    public Map<K,V> map() {
        return origin.stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));
    }
}
