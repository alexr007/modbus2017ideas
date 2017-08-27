package tests.some;

import j2bus.comport.COMPort;
import j2bus.comport.COMPortProperties;
import j2bus.modbus.ModBus;
import j3wad.modules.WAD_DOS_BUS;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by alexr on 08.04.2017.
 */
public class Test_DOS_31 {
    public void run(String portName) throws IOException, InterruptedException {
        ModBus modBus = new ModBus(
            new COMPort(
                portName,
                new COMPortProperties(57600),
                500)
        );
        System.out.println("port opened");
        long delta;
        long millis = System.currentTimeMillis();

        WAD_DOS_BUS dos = new WAD_DOS_BUS(modBus, 0x31);
        CountDownLatch cdl = new CountDownLatch(1);
        new Thread(new Test_Thread_DOS(dos, cdl)).start();
        cdl.await();

        delta = System.currentTimeMillis() - millis;
        System.out.println(
            String.format("Time used in millis %d", millis)
        );
        modBus.finish();
    }
}
