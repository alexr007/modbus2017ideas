package app.web.rs;

import org.takes.Request;
import org.takes.Response;
import org.takes.facets.flash.XeFlash;
import org.takes.facets.fork.FkTypes;
import org.takes.facets.fork.RsFork;
import org.takes.rq.RqHref;
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
public class RsPageMenu extends RsWrap {
    public RsPageMenu(final String xsl, final Request req, final XeSource... src) throws IOException {
        this(xsl, req, Arrays.asList(src));
    }

    public RsPageMenu(final String xsl, final Request req,  final Iterable<XeSource> src) throws IOException {
        super(RsPageMenu.make(xsl, req, src));
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
                new XeLink("show", (new RqHref.Smart(new RqHref.Base(req))).home()+"config/show", "text/xml"),
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
