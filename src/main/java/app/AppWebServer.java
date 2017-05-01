package app;

import common.sw.layers.BIOcore;

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
/*
        System.out.println("T2:WebServer Thread Started.");
        System.out.println("T2:WebServer Thread Finished.");
*/
    }
}
