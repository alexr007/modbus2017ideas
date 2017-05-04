package app.web.rs;

import org.takes.Request;
import org.takes.Response;
import org.takes.facets.flash.XeFlash;
import org.takes.facets.fork.FkTypes;
import org.takes.facets.fork.RsFork;
import org.takes.rs.RsPrettyXml;
import org.takes.rs.RsWithType;
import org.takes.rs.RsWrap;
import org.takes.rs.RsXslt;
import org.takes.rs.xe.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by alexr on 02.05.2017.
 */
public class RsPageList extends RsWrap {
    public RsPageList(final String xsl, final Request req, final XeSource... src) throws IOException {
        this(xsl, req, Arrays.asList(src));
    }

    public RsPageList(final String xsl, final Request req,  final Iterable<XeSource> src) throws IOException {
        super(RsPageList.make(xsl, req, src));
    }

    private static Response make(final String xsl, final Request req, final Iterable<XeSource> src) throws IOException {
        final Response raw = new RsXembly(
            new XeStylesheet(xsl),
            new XeAppend(
                "page",
                new XeMillis(false),
                new XeChain(src),
                new XeLinkHome(req),
                new XeLinkSelf(req),
                new XeMillis(true),
                new XeDate(),
                new XeSla(),
                new XeLocalhost(),
                new XeFlash(req)
            )
        );

        return new RsFork(
            req,
            new FkTypes(
                "application/xml,text/xml",
                new RsPrettyXml(
                    new RsWithType(raw, "text/xml")
                )
            ),
            new FkTypes(
                "*/*",
                new RsXslt(
                    new RsWithType(raw, "text/html"))
            )
        );
    }

}
