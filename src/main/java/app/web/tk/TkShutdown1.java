package app.web.tk;

import org.takes.Take;
import org.takes.tk.TkWrap;

import java.io.IOException;

/**
 * Created by mac on 19.06.2017.
 */
public class TkShutdown1 extends TkWrap {
    public TkShutdown1(Take take) throws IOException {
        super(take);
        Runtime runtime = Runtime.getRuntime();
        Process exec = runtime.exec("shutdown -s -t 0");
        System.exit(0);

/*
        Runtime
            .getRuntime()
            .exec("shutdown -s -t 0");
*/
    }
}
