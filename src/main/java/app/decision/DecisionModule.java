package app.decision;

import app.persistence.init.dev.ModBusDevices;

import static java.lang.Thread.sleep;

/**
 * Created by alexr on 10.02.2017.
 */
abstract public class DecisionModule implements Runnable {
    private final int cycleMs;
    protected final ModBusDevices modBusDevices;
    private boolean finished = false;

    public DecisionModule(int CycleMs, ModBusDevices modBusDevices) {
        this.cycleMs = CycleMs;
        this.modBusDevices = modBusDevices;
    }

    public void finish() {
        finished = true;
    }

    protected abstract void task();

    @Override
    public void run() {
        while (!finished) {
            long timeBegin = System.currentTimeMillis();
            task();
            long timeDelta = System.currentTimeMillis() - timeBegin;
            if (cycleMs>timeDelta) {
                try {
                    sleep(cycleMs-timeDelta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
