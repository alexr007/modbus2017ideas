package tests.some;

import org.takes.http.Exit;
import org.takes.http.FtCli;
import web.tk.TkWebApp;

import java.io.IOException;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_WebServer {
    public static void main(String[] args) throws IOException {
        new FtCli(new TkWebApp(), args).start(Exit.NEVER);
    }
}
