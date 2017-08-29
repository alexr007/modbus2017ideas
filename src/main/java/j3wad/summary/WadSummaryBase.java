package j3wad.summary;

import jssc.SerialPortException;
import j3wad.modules.WadAbstractDevice;
import org.xembly.Directives;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by alexr on 28.04.2017.
 */
abstract public class WadSummaryBase implements WadSummary {
    protected final WadAbstractDevice device;

    public WadSummaryBase(WadAbstractDevice device) {
        this.device = device;
    }

    abstract protected Map<Integer, ValuePresented> map() throws IOException;

    public Directives xmlDir() throws IOException {
        Directives dirs = new Directives();
        map().forEach((k, v) ->
            dirs.add("channel")
                .add("id").set(k).up()
                .add("value").set(v.toString()).up()
                .up()
        );
        return dirs;
    }

    public String txt() throws IOException {
        return String.format("details:\n%s",
            map().entrySet().stream()
                .map(ent->String.format("Channel %2d: %s\n", ent.getKey(), ent.getValue()))
                .collect(Collectors.joining()));
    }
}
