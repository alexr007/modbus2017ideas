package app.web.test;

import app.web.ParsedDeviceData;
import app.web.ValueValidated;
import jbus.modbus.InvalidModBusFunction;
import jwad.modules.WadAbstractDevice;
import common.sw.layers.BIOcore;
import jssc.SerialPortException;
import org.takes.Request;

import java.io.IOException;

/**
 * Created by alexr on 02.05.2017.
 */
public class ParsedDeviceDataTest {
    private final ParsedDeviceData parsed;

    public ParsedDeviceDataTest(final Request rq) {
        this.parsed = new ParsedDeviceData(rq);
    }

    public String testReadQuery() throws IOException {
        return
            String.format("href: %s\nnoparam: %s\nhome: %s\npath: %s\nd: %s\nc: %s\nv: %s",
                parsed.href(),
                parsed.noparam(),
                parsed.home(),
                parsed.path(),
                parsed.device(),
                parsed.channel(),
                parsed.value()
            );
    }

    public CharSequence testDeviceRead(final BIOcore core) throws IOException {
        return core.dev(parsed.device()).summaryTxt();
    }

    public void testDeviceWrite(final BIOcore core) throws IOException, InvalidModBusFunction, SerialPortException {
        if (parsed.hasEnoughParams()){
            WadAbstractDevice dev = core.dev(parsed.device());
            dev.channel(Integer.valueOf(parsed.channel()))
                .set( new ValueValidated(dev.type()).value(parsed.value()) );
        }
    }
}
