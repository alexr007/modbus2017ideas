package common.modbus.response;

import common.IntToArray;
import common.IterableToString;
import java.util.Arrays;

/**
 * Created by alexr on 22.01.2017.
 */
public interface Values {

    public boolean has();
    public boolean single();
    public int get();
    public int get(int index);
    public int count();

    public class None implements  Values {
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

    public class Single implements  Values {
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
    }

    public class Multiple implements Values {
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
            return new IterableToString<Integer>(
                Arrays.stream(values).boxed().toArray( Integer[]::new )
            ).toString();
        }
    }

}
