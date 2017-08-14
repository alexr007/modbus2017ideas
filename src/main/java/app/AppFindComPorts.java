package app;

import j2bus.comport.COMPortList;
/**
 * Created by alexr on 08.04.2017.
 */
public class AppFindComPorts {
    public void run() {
        System.out.println(
            String.format("Available COM-port set: %s",
                new COMPortList().toString()
            )
        );
    }
}
