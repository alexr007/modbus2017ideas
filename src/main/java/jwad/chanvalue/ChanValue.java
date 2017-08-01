package jwad.chanvalue;

/**
 * Created by alexr on 25.04.2017.
 *
 * implementation in one line all signal types
 * ChanValue value = new ChanValue.A(1000);
 * ChanValue value = new ChanValue.DI(TypeDI.CLOSED);
 * ChanValue value = new ChanValue.DO(TypeDO.ON);
 *
 * ChanValue value = chanValue.get()
 * TypeChan type = chanValue.type()
 * see ChanValueTest for full Explanation.
 *
 */
public interface ChanValue<T> {
    TypeChan type();
    default T get() {
        throw new IllegalStateException("unsupported operation on interface ChanValue");
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
        public String toString() {
            return String.format("A:%s",this.value.toString());
        }
    }

    class DI implements ChanValue {
        private final TypeDI value;

        public DI(TypeDI value) {
            this.value = value;
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
        public String toString() {
            return String.format("DI:%s",this.value.toString());
        }
    }

    class DO implements ChanValue {
        private final TypeDO value;

        public DO(TypeDO value) {
            this.value = value;
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
        public String toString() {
            return String.format("DO:%s",this.value.toString());
        }
    }

}
