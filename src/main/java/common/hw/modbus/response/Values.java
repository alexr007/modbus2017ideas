package common.hw.modbus.response;

import common.IntToArray;
import common.IterableToString;
import java.util.Arrays;

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
    }

    class Single implements  Values {
        private final int value;

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
        public String toString() {
            return "Single:"+get();
        }
    }

    class Multiple implements Values {
        private final int[] values;

        public Multiple(int[] values) {
            this.values = values;
        }

        public Multiple(IntToArray values) {
            this(values.get());
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
        public String toString() {
            return
                String.format("Multiple:",
                    new IterableToString<Integer>(
                        Arrays.stream(values).boxed().toArray(Integer[]::new)
                    ).toString()
                );
        }
    }

}
