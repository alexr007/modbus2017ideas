package web.tk;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;

import java.io.IOException;

/**
 * Created by alexr on 10.01.2017.
 */
public final class TkIndex implements Take {
    private final int index;

    public TkIndex(int index) {
        this.index = index;
    }

    @Override
    public Response act(Request req) throws IOException {
        return
            new RsWithStatus(
                new RsWithType(
                    new RsWithBody("<html>index"+index +"</html>"),
                    "text/html"
                ),
                200
            );
    }
}
