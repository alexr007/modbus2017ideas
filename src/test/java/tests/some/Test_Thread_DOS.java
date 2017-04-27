package tests.some;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.wad.ModBusAbstractDevice;
import jssc.SerialPortException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by alexr on 08.04.2017.
 */
public class Test_Thread_DOS implements Runnable{
    private final ModBusAbstractDevice device;
    private final CountDownLatch cdl;
    private Object monitor = new Object();
    private final int latency = 500;
    private final int count = 36000;

    public Test_Thread_DOS(ModBusAbstractDevice device, CountDownLatch cdl) {
        this.device = device;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        synchronized (monitor) {
            try {
                device.channel(0).off();
                for (int i = 1; i <= count; i++) {
                    final int chan = i % 8 +1;
                    device.channel(chan).on();
                    monitor.wait(latency);
                    device.channel(chan).off();
                    monitor.wait(latency);
                    System.out.println(chan);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvalidModBusFunction e) {
                e.printStackTrace();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
            cdl.countDown();
        }
    }
}
