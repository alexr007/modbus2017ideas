package app.web.test;

/**
 * Created by alexr on 02.05.2017.
 */
public class ParsedDeviceDataTest {
 /*   private final ParsedDeviceData parsed;

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

    public CharSequence testDeviceRead(final BIOcore j4core) throws IOException {
        return j4core.dev(parsed.device()).summaryTxt();
    }

    public void testDeviceWrite(final BIOcore j4core) throws IOException, InvalidModBusFunction, SerialPortException {
        if (parsed.hasEnoughParams()){
            WadAbstractDevice dev = j4core.dev(parsed.device());
            dev.channel(Integer.valueOf(parsed.channel()))
                .set( new ValueValidated(dev.type()).value(parsed.value()) );
        }
    }*/
}
