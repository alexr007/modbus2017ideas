package app.persistence.chanvalue;

/**
 * Created by alexr on 25.04.2017.
 *
 * implementation in one line all signal types
 * ChanValue value = new ChanValue.A(1000);
 * ChanValue value = new ChanValue.DI(TypeDI.CLOSED);
 * ChanValue value = new ChanValue.DO(TypeDO.ON);
 *
 * see ChanValueTest for full Explanation.
 *
 */
public interface ChanValue {

    TypeChan type();

/*
    default int getA() throws Exception {
        throw new Exception("unsupported operation");
    }

    default TypeDI getDI() throws Exception {
        throw new Exception("unsupported operation");
    }

    default TypeDO getDO() throws Exception {
        throw new Exception("unsupported operation");
    }
*/

    class A implements ChanValue {
        private final int value;

        public A(int value) {
            this.value = value;
        }

        @Override
        public TypeChan type() {
            return TypeChan.A;
        }

        //@Override
        public int get() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.format("A:%d",this.value);
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

        //@Override
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

        //@Override
        public TypeDO get() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.format("DO:%s",this.value.toString());
        }
    }
}
