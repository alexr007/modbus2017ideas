package app.web.tk;

import app.web.ParsedDeviceData;
import app.web.ValueValidated;
import app.web.rs.RsPageDevice;
import jwad.ModBusAbstractDevice;
import common.sw.layers.BIOcore;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;
import org.takes.rs.RsWrap;
import org.takes.rs.xe.*;
import org.takes.tk.TkRedirect;

import java.io.IOException;

/**
 * Created by alexr on 01.05.2017.
 */
public class TkShowDevice implements Take {
    private final BIOcore core;

    public TkShowDevice(BIOcore core) {
        this.core = core;
    }

    @Override
    public Response act(Request request) throws IOException {
        ParsedDeviceData parsed = new ParsedDeviceData(request);
        RsWrap res;
        if (parsed.hasEnoughParams()) {
            // has enough parameters to write data to channel
            ModBusAbstractDevice dev = core.dev(parsed.device());
            try {
                dev.channel(Integer.valueOf(parsed.channel()))
                    .set( new ValueValidated(dev.type()).value(parsed.value()) );
            } catch (Exception e) {
                e.printStackTrace();
            }
            // redirect
            res = new RsPrint(
                    new TkRedirect(parsed.noparamnoslash()).act(new RqFake())
                );
        }
        else {
            res = new RsPageDevice(
                "/xsl/device.xsl",
                request,
                new XeDirectives( core.dev( parsed.device() ).xml() )
            );
        }
        return res;
    }
    // TODO: All pages must have button to go to UPPER level
}
