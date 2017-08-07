package jbus.modbus.response;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is mapped values:
 * source: IntStrean
 * converted to List<T>
 */
public class ValuesMapped<T> {
    private final Values origin;
    private final MappedTo<T> mapper;

    public ValuesMapped(Values origin, MappedTo<T> mapper) {
        this.origin = origin;
        this.mapper = mapper;
    }

    /**
     * retrieve the list of Values
     * mapped to T by MappedTo<T>.map
     */
    public List<T> list() {
        return origin.stream()
            .mapToObj(mapper::map)
            .collect(Collectors.toList());
    }

    private AbstractMap.SimpleEntry<Integer, T> mapKeyToEntry(int index) {
        return new AbstractMap.SimpleEntry<>(index, mapper.map(origin.get(index)));
    }

    public Map<Integer, T> map() {
        return
            IntStream.rangeClosed(1,origin.count())
            .mapToObj(this::mapKeyToEntry)
            .collect(Collectors.toMap(
                AbstractMap.SimpleEntry::getKey,
                AbstractMap.SimpleEntry::getValue
            ));
    }

    /**
     * check if its single value
     */
    boolean single() {
        return origin.single();
    }

    /**
     * return single Value
     * mapped to T by MappedTo<T>.map
     */
    T get() {
        return mapper.map(origin.get());
    }
}
