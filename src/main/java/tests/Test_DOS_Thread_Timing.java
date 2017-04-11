package tests;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by alexr on 11.04.2017.
 */
public class Test_DOS_Thread_Timing implements Runnable {
    private final ModBusAbstractDevice device;
    private final CountDownLatch cdl;
    private Object monitor = new Object();
    private final int count = 1000;

    public Test_DOS_Thread_Timing(ModBusAbstractDevice device, CountDownLatch cdl) {
        this.device = device;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        int t = 0;
        synchronized (monitor) {
            try {
                for (int i = 1; i <= count; i++) {
                    t += device.temperature();
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            } catch (InvalidModBusResponse invalidModBusResponse) {
                invalidModBusResponse.printStackTrace();
            } catch (InvalidModBusFunction invalidModBusFunction) {
                invalidModBusFunction.printStackTrace();
            }
            cdl.countDown();
            System.out.println(t);
        }
    }
}
