package app.web;

import common.sw.layers.BIOcore;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.*;
import org.takes.tk.*;

import java.io.*;

/**
 * Created by alexr on 01.05.2017.
 */
public class TkApp extends TkWrap {
    public TkApp(final BIOcore core) throws IOException {
        super(
//            new TkWithHeaders(
//                new TkVersioned(
                    new TkMeasured(
//                        new TkGzip(
//                            new TkFlash(
                                new TkAppFallback(
                                    new TkFork(
                                        // JPG
                                        new FkRegex(
                                            "/images/[a-z0-9]+\\.jpg",
                                            new TkWithType(
                                                new TkFiles("."), // в конструкторе папка где скать файлы
                                                "image/jpeg"
                                            )
                                        ),
                                        // PNG
                                        new FkRegex(
                                            "/images/[a-z0-9]+\\.png",
                                            new TkWithType(
                                                new TkFiles("."), // в конструкторе папка где скать файлы
                                                "image/png"
                                            )
                                        ),
                                        // SVG
                                        new FkRegex(
                                            "/images/[a-z0-9\\-]+\\.svg",
                                            new TkWithType(
                                                new TkFiles("."), // в конструкторе папка где скать файлы
                                                "image/svg+xml"
                                            )
                                        ),
                                        // XSL
                                        new FkRegex(
                                            "/xsl/[a-z\\-]+\\.xsl",
                                            new TkWithType(
                                                new TkFiles("."), // в конструкторе папка где скать файлы
                                                "text/xsl"
                                            )
                                        ),
                                        // CSS
                                        new FkRegex(
                                            "/css/[a-z\\-]+\\.css",
                                            new TkWithType(
                                                new TkFiles("."), // в конструкторе папка где скать файлы
                                                "text/css"
                                            )
                                        ),
                                        new FkRegex("/", new TkIndex(core)),
                                        new FkRegex("/config", new TkShowBasicMenu(core)),
                                        new FkRegex("/config/show", new TkShowDeviceList(core)),
                                        new FkRegex("/config/show/[A-Za-z0-9\\_\\-]+", new TkShowDevice(core))
                                    )
//                                )
//                            )
                        )
//                    )
//                ),
//                "Vary: CookieXXX"
            )
        );
    }
}
