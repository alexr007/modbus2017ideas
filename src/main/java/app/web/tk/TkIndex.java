package app.web.tk;

import app.web.rs.RsPage;
import j4core.BIOcore;
import org.javatuples.Triplet;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directives;

import java.io.IOException;

/**
 * Created by alexr on 01.05.2017.
 */
final class TkIndex implements Take {
    private final BIOcore core;

    TkIndex(final BIOcore core) {
        this.core = core;
    }

    @Override
    public Response act(final Request req) throws IOException {
/*
        new FileOutputStream(new File("ABCD")) {{
           write(32);
           close();
        }};

*/
        return new RsPage(
            "/xsl/index.xsl",
            req);

/*
        return new RsPage(
            "/xsl/index.xsl",
            req,
            new XeAppend(
                "domains",
                new XeTransform<>(
                    //this.base.all(),
                    new ArrayList<Triplet>() {{
                        add(new Triplet("A","B","C"));
                        add(new Triplet("D","E","F"));
                        add(new Triplet("G","H","I"));
                    }},
                    TkIndex::source
                )
            )
        );
*/
    }

    /**
     * Convert event to Xembly.
     * @param domain The event
     * @return Xembly
     * @throws IOException If failsRaw
     */
    private static XeSource source(final Triplet triplet) throws IOException {
        return new XeDirectives(
            new Directives()
                .add("domain")
                .add("name").set(triplet.getValue0()).up()
                .add("owner").set(triplet.getValue1()).up()
                .add("usage").set(triplet.getValue2()).up()
                .up()
        );
    }

}
