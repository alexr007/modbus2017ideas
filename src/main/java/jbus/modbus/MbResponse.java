package jbus.modbus;

import common.sw.common.BytesAsHex;

/**
 * Created by alexr on 20.01.2017.
 */
public interface MbResponse {

    public boolean has();
    public byte[] get();

    public class Data implements MbResponse {
        private final byte[] data;

        public Data(byte[] data) {
            this.data = data;
        }

        @Override
        public boolean has() {
            return true;
        }

        @Override
        public byte[] get() {
            return data;
        }

        @Override
        public String toString() {
            return new BytesAsHex(data).toString();
        }
    }

    public class Empty implements MbResponse  {

        @Override
        public boolean has() {
            return false;
        }

        @Override
        public byte[] get() {
            return new byte[0];
        }

        @Override
        public String toString() {
            return "Empty";
        }
    }
}
