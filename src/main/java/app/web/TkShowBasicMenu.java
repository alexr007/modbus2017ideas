package app.web;

import common.sw.layers.BIOcore;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsText;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexr on 01.05.2017.
 */
public class TkShowBasicMenu implements Take {
    private final BIOcore core;

    public TkShowBasicMenu(BIOcore core) {
        this.core = core;
    }

    @Override
    public Response act(Request request) throws IOException {
        return new RsPageMenu(
            "/xsl/menu.xsl",
            request);
    }
}
