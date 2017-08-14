package jwad.chanvalue;

import static jwad.chanvalue.TypeDO.*;

/**
 * Created by alexr on 25.04.2017.
 *
 * implementation in one line all signal types
 * ChanValue value = new ChanValue.A(1000);
 * ChanValue value = new ChanValue.DI(TypeDI.CLOSED);
 * ChanValue value = new ChanValue.DO(TypeDO.ON);
 *
 * ChanValue value = chanValue.bytes()
 * TypeChan type = chanValue.type()
 * see ChanValueTest for full Explanation.
 *
 */
public interface ChanValue<T> {
    TypeChan type();
    default T get() {
        throw new IllegalStateException("unsupported operation on interface ChanValue");
    }
    default boolean has() {return false;}

    static ChanValue.A A(int val) {
        return new ChanValue.A(val);
    }

    static ChanValue.DI DI(TypeDI val) {
        return new ChanValue.DI(val);
    }

    static ChanValue.DO DO(TypeDO val) {
        return new ChanValue.DO(val);
    }
    static ChanValue.DO DO_ON() {
        return new ChanValue.DO(TypeDO.ON);
    }
    static ChanValue.DO DO_OFF() {
        return new ChanValue.DO(TypeDO.OFF);
    }

    static ChanValue.None None() {
        return new ChanValue.None();
    }

    class A implements ChanValue {
        private final Integer value;

        public A(Integer value) {
            this.value = value;
        }

        @Override
        public TypeChan type() {
            return TypeChan.A;
        }

        @Override
        public Integer get() {
            return this.value;
        }

        @Override
        public boolean has() {
            return true;
        }

        @Override
        public String toString() {
            return String.format("A:%s",this.value.toString());
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ChanValue.A
            && value.equals(((A) obj).value);
        }
    }

    class DI implements ChanValue {
        private final TypeDI value;

        public DI(TypeDI value) {
            this.value = value;
        }

        public DI(int value) {
            switch (value) {
                case 0: this.value = TypeDI.OPENED;
                        break;
                case 1: this.value = TypeDI.CLOSED;
                        break;
                case 2: this.value = TypeDI.FAIL;
                        break;
                default: throw new IllegalArgumentException("TypeDI parameter invalid: should be 0..2");
            }
        }

        @Override
        public TypeChan type() {
            return TypeChan.DI;
        }

        @Override
        public TypeDI get() {
            return this.value;
        }

        @Override
        public boolean has() {
            return true;
        }

        @Override
        public String toString() {
            return String.format("DI:%s",this.value.toString());
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ChanValue.DI
                && value.equals(((DI) obj).value);
        }
    }

    class DO implements ChanValue {
        private final TypeDO value;

        public DO(TypeDO value) {
            this.value = value;
        }

        public DO(int value) {
            switch (value) {
                case 0: this.value = TypeDO.OFF;
                    break;
                case 1: this.value = ON;
                    break;
                default: throw new IllegalArgumentException("TypeDO parameter invalid: should be 0..1");
            }
        }

        @Override
        public TypeChan type() {
            return TypeChan.DO;
        }

        @Override
        public TypeDO get() {
            return this.value;
        }

        @Override
        public boolean has() {
            return true;
        }

        @Override
        public String toString() {
            return String.format("DO:%s",this.value.toString());
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ChanValue.DO
                && value.equals(((DO) obj).value);

        }
    }

    class None implements ChanValue{
        @Override
        public TypeChan type() {
            return TypeChan.None;
        }
    }


}
