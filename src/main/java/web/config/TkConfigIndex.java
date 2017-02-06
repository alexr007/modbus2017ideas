package web.config;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;
import web.config.xml.FileEx;
import web.config.xml.XemblyExample;
import org.xembly.Directives;
import org.xembly.Xembler;

import java.io.IOException;

/**
 * Created by alexr on 10.01.2017.
 */
public final class TkConfigIndex implements Take {

    @Override
    public Response act(Request req) throws IOException {
        String xml = null;
        try {
            xml = new XemblyExample().test();
            //new FileEx().test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RsWithBody(xml);
    }
}
