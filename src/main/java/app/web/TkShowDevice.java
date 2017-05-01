package app.web;

import com.google.common.base.Joiner;
import common.sw.layers.BIOcore;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.TkRegex;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Href href = new RqHref.Base(request).href();
        String[] split = href.toString().split("/");
        String s = split[split.length - 1];
        return new RsText(core.dev(s).summary());
    }
}
