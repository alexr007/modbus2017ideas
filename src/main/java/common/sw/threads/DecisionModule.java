package common.sw.threads;

import common.hw.modbus.ModBus;
import common.sw.ModBusDevices;

import static java.lang.Thread.sleep;

/**
 * Created by alexr on 10.02.2017.
 */
abstract public class DecisionModule implements Runnable{
    private final int freqency;
    private final ModBusDevices modBusDevices;
    private boolean finished = false;

    public DecisionModule(int freqency, ModBusDevices modBusDevices) {
        this.freqency = freqency;
        this.modBusDevices = modBusDevices;
    }

    public void finish() {
        finished = true;
    }

    protected abstract void task();

    @Override
    public void run() {
        while (!finished) {
            task();
            try {
                sleep(freqency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
