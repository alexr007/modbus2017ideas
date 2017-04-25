package common.sw.persistence;

import common.hw.modbus.device.SignalType;

/**
 * Created by alexr on 25.04.2017.
 */
public interface PortValue {

    AbtractValue value = null;
    SignalType type(); // Analog, Digital

    class A implements  PortValue {
        public A(AbtractValue value) {
        }

        @Override
        public SignalType type() {
            return SignalType.Analog;
        }
    }

    class D implements  PortValue {
        public D(AbtractValue value) {
        }

        @Override
        public SignalType type() {
            return SignalType.Digital;
        }
    }
}
