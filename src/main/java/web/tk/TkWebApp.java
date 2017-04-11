package web.tk;

import com.jcabi.log.VerboseProcess;
import com.jcabi.manifests.Manifests;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.TkSecure;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.*;
import org.takes.facets.forward.TkForward;
import org.takes.misc.Opt;
import org.takes.rq.RqHref;
import org.takes.rs.RsText;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;
import org.takes.tk.*;
import web.config.TkConfigIndex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/*
This is root web-app, that running.
 */
public final class TkWebApp extends TkWrap {
    // App revision
    private static final String REV = "2016.01.15";
    // Persistence
    private static int index=0;

    // Ctor
    public TkWebApp() {
        super(
            new Take() {
                @Override
                public Response act(Request req) throws IOException {
                    return
                        new TkMeasured(new TkConfigIndex()).act(req);



/*
                    new RsText(
                        new RqHref.Base(req).href()
                    )
*/


                }
            }
        );
    }

    // Ctor

    private static Take make_yegor() throws IOException {
        if (!"UTF-8".equals(Charset.defaultCharset().name())) {
            throw new IllegalStateException(
                String.format(
                    "default encoding is %s", Charset.defaultCharset()
                )
            );
        }
        return new TkWithHeaders(
            new TkVersioned(
                new TkMeasured(
                    new TkGzip(
                        new TkFlash(
                            new TkAppFallback(
                                //new TkAppAuth(
                                new TkForward(
                                    TkWebApp.regex()
                                )
                                //)
                            )
                        )
                    )
                )
            ),
            String.format("X-Jscada-Revision: %s", TkWebApp.REV)
        );

    }
    /*
    This is path parser
     */
    private static Take regex() throws IOException {
        return new TkFork(
            new FkRegex("/hr", new RsText(
                ""
            )
            ),
            new FkRegex("/config",
                new TkMeasured(new TkConfigIndex())

            ),
            new FkRegex("/", new TkIndex(index++))

/*            new FkHost(
                "relay.jare.io",
                new TkFallback(
                    new TkRelay(base),
                    req -> new Opt.Single<>(
                        new RsWithType(
                            new RsWithBody(
                                new RsWithStatus(req.code()),
                                String.format(
                                    // @checkstyle LineLength (1 line)
                                    "Please, submit this stacktrace to GitHub and we'll try to help: https://github.com/yegor256/jare/issues\n\n%s",
                                    ExceptionUtils.getStackTrace(
                                        req.throwable()
                                    )
                                )
                            ),
                            "text/plain"
                        )
                    )
                )
            ),
            new FkRegex("/robots.txt", ""),
            new FkRegex(
                "/xsl/[a-z\\-]+\\.xsl",
                new TkWithType(
                    TkApp.refresh("./src/main/xsl"),
                    "text/xsl"
                )
            ),
            new FkRegex(
                "/css/[a-z]+\\.css",
                new TkWithType(
                    TkApp.refresh("./src/main/scss"),
                    "text/css"
                )
            ),
            new FkRegex(
                "/images/[a-z]+\\.svg",
                new TkWithType(
                    TkApp.refresh("./src/main/resources"),
                    "image/svg+xml"
                )
            ),
            new FkRegex(
                "/images/[a-z]+\\.png",
                new TkWithType(
                    TkApp.refresh("./src/main/resources"),
                    "image/png"
                )
            ),
            new FkRegex("/", new TkIndex(base)),
            new FkRegex(
                "/invalidate",
                new TkInvalidate(
                    Manifests.read("Jare-CloudFrontKey"),
                    Manifests.read("Jare-CloudFrontSecret")
                )
            ),
            new FkAuthenticated(
                new TkSecure(
                    new TkFork(
                        new FkRegex("/domains", new TkDomains(base)),
                        new FkRegex(
                            "/add",
                            new TkMethods(new TkAdd(base), "POST")
                        ),
                        new FkRegex("/delete", new TkDelete(base))
                    )
                )
            )*/
        );
    }


    private static Take refresh(final String path) throws IOException {
        return new TkFork(
            new FkHitRefresh(
                new File(path),
                new Runnable() {
                    @Override
                    public void run() {
                        new VerboseProcess(
                            new ProcessBuilder(
                                "mvn",
                                "generate-resources"
                            )
                        ).stdout();
                    }
                },
                new TkFiles("./target/classes")
            ),
            new FkFixed(new TkClasspath())
        );
    }
}