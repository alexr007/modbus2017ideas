package common.sw.decision;

import common.hw.modbus.response.Values;

/**
 * Created by alexr on 24.04.2017.
 */
public class FakeReader implements PortReader {
    @Override
    public Values get(CharSequence name) {
        System.out.println(
            String.format("Reading value:%s", name)
        );
        return new Values.Single((int) (Math.random()*10+10));
    }
}
