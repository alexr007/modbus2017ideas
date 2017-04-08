package tests;

import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.ModBusInvalidFunction;
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

    public Test_Thread_DOS(ModBusAbstractDevice device, CountDownLatch cdl) {
        this.device = device;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        synchronized (monitor) {
            try {
                device.channel(0).off();
                for (int i = 1; i <= 8; i++) {
                    device.channel(i).on();
                    monitor.wait(latency);
                    device.channel(i).off();
                    monitor.wait(latency);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SerialPortException e) {
                e.printStackTrace();
            } catch (ModBusInvalidFunction e) {
                e.printStackTrace();
            }
            cdl.countDown();
        }
    }
}
