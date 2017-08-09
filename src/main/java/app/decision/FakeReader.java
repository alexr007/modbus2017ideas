package app.decision;

import jbus.modbus.response.Values;

/**
 * Created by alexr on 24.04.2017.
 */
public class FakeReader implements PortReader {
    @Override
    public Values get(CharSequence name) {
        System.out.println(
            String.format("Reading value:%s", name)
        );
        return null;
/*
        return new Values.Single((iface) (Math.random()*10+10));
*/
    }
}
