package app.web;

import common.sw.layers.BIOcore;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.fork.TkRegex;
import org.takes.rs.RsText;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alexr on 01.05.2017.
 */
public class TkShowBasicMenu implements Take {
    public TkShowBasicMenu(BIOcore core) {
    }

    @Override
    public Response act(Request request) throws IOException {
        List<String> list = Arrays.asList("show");
        return
            new RsText(list.toString());
    }
}
