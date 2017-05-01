package app.web;

import common.sw.layers.BIOcore;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.TkRegex;
import org.takes.rs.RsText;

import java.io.IOException;

/**
 * Created by alexr on 01.05.2017.
 */
public class TkShowDeviceList implements Take {
    private final BIOcore core;

    public TkShowDeviceList(BIOcore core) {
        this.core=core;
    }

    @Override
    public Response act(Request request) throws IOException {
        return new RsText(core.devList().toString());
    }
}
