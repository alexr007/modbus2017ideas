package app;

import app.web.tk.TkApp;
import app.persistence.BIOcore;
import org.takes.http.Exit;
import org.takes.http.FtCli;
import java.io.IOException;

/**
 * Created by alexr on 01.05.2017.
 */
public class AppWebServer implements Runnable {
    private final BIOcore core;

    public AppWebServer(BIOcore core) {
        this.core = core;
    }

    @Override
    public void run() {
        String commandLine = "--port=8080";
        try {
            new FtCli(new TkApp(core), commandLine).start(Exit.NEVER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
