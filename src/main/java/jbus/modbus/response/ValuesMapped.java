package jbus.modbus.response;

import java.util.List;
import java.util.stream.Collectors;

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
