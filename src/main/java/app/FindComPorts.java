package app;

import common.hw.comport.COMPortList;
/**
 * Created by alexr on 08.04.2017.
 */
public class FindComPorts {
    public void run() {
        System.out.println(
            String.format("Available COM-port list: %s",
                new COMPortList().toString()
            )
        );
    }
}
