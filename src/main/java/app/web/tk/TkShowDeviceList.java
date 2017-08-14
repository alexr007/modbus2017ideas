package app.web.tk;

import app.web.rs.RsPageList;
import j4core.BIOcore;
import org.javatuples.Triplet;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeTransform;
import org.xembly.Directives;
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
        return new RsPageList(
            "/xsl/set.xsl",
            request,
            new XeAppend(
                "devices",
                new XeTransform<>(
                    core.devListTriplet(),
                    TkShowDeviceList::source
                )
            )
        );
    }

    private static XeSource source(final Triplet triplet) throws IOException {
        return new XeDirectives(
            new Directives()
                .add("device")
                .add("name").set(triplet.getValue0().toString()).up()
                .add("type").set(triplet.getValue1().toString()).up()
                .add("id").set(triplet.getValue2()).up()
                .up()
        );
    }
}
