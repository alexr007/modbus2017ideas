package app.web;

import org.takes.Request;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import java.io.IOException;

/**
 * Created by alexr on 02.05.2017.
 */
public class ParsedDeviceData {
    private final RqHref.Base hrefBase;
    private final RqHref.Smart hrefSmart;

    public ParsedDeviceData(Request rq) {
        hrefBase = new RqHref.Base(rq);
        hrefSmart = new RqHref.Smart(hrefBase);
    }

    // full href via request : http://127.0.0.1:8080/config/show/DEV31?c=8&v=on
    public Href href() throws IOException {
        return hrefBase.href();
    }

    // request w/o params: http://127.0.0.1:8080//config/show/DEV31
    public String noparam() throws IOException {
        return String.format("%s%s", home(), path());
    }

    // request home: http://127.0.0.1:8080/
    public String home() throws IOException {
        return hrefSmart.home().toString();
    }

    // request path /config/show/DEV31
    public String path() throws IOException {
        return href().path();
    }

    // request device: DEV31
    public String device() throws IOException {
        String[] splitted = path().split("/");
        return splitted[splitted.length - 1];
    }

    public boolean hasEnoughParams() throws IOException {
        return
            (!param("c").equals("DEF"))&&
            (!param("v").equals("DEF"));
    }

    // param
    public String param(CharSequence name) throws IOException {
        return
            //href().param(name).iterator().next();
            hrefSmart.single(name,"DEF");
    }

    // channel c=8
    public int channel() throws IOException {
        return Integer.valueOf(
            param("c")
        );
    }

    // value v=on
    public String value() throws IOException {
        return
            param("v");
    }
}
