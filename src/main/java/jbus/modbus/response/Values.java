package jbus.modbus.response;

import jbase.arr.ArrayFromInt;
import jbase.IterableToString;
import java.util.stream.IntStream;

/**
 * Created by alexr on 22.01.2017.
 *
 */
public interface Values {

    boolean has();
    boolean single();
    int get();
    int get(int index);
    int count();
    IntStream stream();

    class None implements  Values {
        @Override
        public boolean has() {
            return false;
        }

        @Override
        public boolean single() {
            return false;
        }

        @Override
        public int get() {
            return 0;
        }

        @Override
        public int get(int index) {
            return 0;
        }

        @Override
        public int count() {
            return 0;
        }

        @Override
        public IntStream stream() {
            return IntStream.empty();
        }
    }

    class Single implements  Values {
        private final int value;

        public Single(WordsFromBytes origin) {
            this(origin.get0());
        }

        public Single(int value) {
            this.value = value;
        }

        @Override
        public boolean has() {
            return true;
        }

        @Override
        public boolean single() {
            return true;
        }

        @Override
        public int get() {
            return this.value;
        }

        @Override
        public int get(int index) {
            return this.value;
        }

        @Override
        public int count() {
            return 1;
        }

        @Override
        public IntStream stream() {
            return IntStream.of(value);
        }

        @Override
        public String toString() {
            return "Single:"+get();
        }
    }

    class Multiple implements Values {
        private final int[] values;

        public Multiple(WordsFromBytes origin) {
            this(origin.get());
        }

        public Multiple(ArrayFromInt values) {
            this(values.get());
        }

        public Multiple(int[] values) {
            this.values = values;
        }

        @Override
        public boolean has() {
            return true;
        }

        @Override
        public boolean single() {
            return false;
        }

        @Override
        public int get() {
            throw new IllegalArgumentException();
        }

        @Override
        public int get(int index) {
            return values[index-1];
        }

        @Override
        public int count() {
            return values.length;
        }

        @Override
        public IntStream stream() {
            return IntStream.of(values);
        }

        @Override
        public String toString() {
            return
                String.format("Multiple:%s",
                    new IterableToString<Integer>(values)
                );
        }
    }

}
