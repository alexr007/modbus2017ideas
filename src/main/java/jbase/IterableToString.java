package jbase;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.function.*;

/**
 * Created by alexr on 22.01.2017.
 */
public class IterableToString<T> {
    private final T[] origin;
    private final String DELIMITER = ", ";
    private final String HEAD="[";
    private final String TAIL="]";
    private final Function<T, String> transform;

    public IterableToString(Iterable<T> origin) {
        this( (T[]) Stream.of(origin).toArray(), t -> t.toString() );
    }

    public IterableToString(Iterable<T> origin, Function<T, String> transform) {
        this( (T[]) Stream.of(origin).toArray(), transform);
    }

    public IterableToString(int[] origin) {
        this((T[]) IntStream
                .range(0, origin.length)
                .mapToObj(i -> (int) origin[i])
                .toArray()
            , t -> t.toString());
    }

    public IterableToString(int[] origin, Function<T, String> transform) {
        this((T[]) IntStream
                .range(0, origin.length)
                .mapToObj(i -> (int) origin[i])
                .toArray()
            , transform);
    }

    public IterableToString(byte[] origin) {
        this((T[]) IntStream
                .range(0, origin.length)
                .mapToObj(i -> (int) origin[i])
                .toArray()
            , t -> t.toString());
    }

    public IterableToString(byte[] origin, Function<T, String> transform) {
        this((T[]) IntStream
            .range(0, origin.length)
            .mapToObj(i -> (int) origin[i])
            .toArray()
            , transform);
    }

    public IterableToString(T[] origin) {
        this(origin, t->t.toString());
    }

    public IterableToString(T[] origin, Function<T, String> transform) {
        this.origin = origin;
        this.transform = transform;
    }

    @Override
    public String toString() {
        return String.format("%d:%s%s%s",
            origin.length,
            HEAD,
            Stream.of(origin)
                .map(item -> transform.apply(item))
                .collect(Collectors.joining(DELIMITER)),
            TAIL);
    }
}
