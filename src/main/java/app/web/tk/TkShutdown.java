package app.web.tk;

import com.jcabi.log.VerboseProcess;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.tk.TkWrap;
import org.takes.facets.fork.*;
import org.takes.tk.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alexey on 04-May-17.
 */
public class TkShutdown extends TkWrap {

    TkShutdown() throws IOException {
        super(new Take() {
                  @Override
                  public Response act(Request request) throws IOException {
                      VerboseProcess reboot = new VerboseProcess(
                          new ProcessBuilder(
                              "reboot",
                              "generate-resources"
                          ));
                      reboot.stdout();
                      return null;
                  }
              }
        );


        new TkFork(
            new FkHitRefresh(
                new File("path"),
                () -> new VerboseProcess(
                    new ProcessBuilder(
                        "mvn",
                        "generate-resources"
                    )
                ).stdout(),
                new TkFiles("./target/classes")
            )//,
            //new FkFixed(new TkClasspath())
        );
    }
}

