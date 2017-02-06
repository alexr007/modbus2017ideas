package __ideas.threads;

import static java.lang.Thread.sleep;

public class ThreadLogic implements Runnable{

    ThreadSensors sensors;

    public ThreadLogic(ThreadSensors sensors)
    {
        this.sensors = sensors;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++)
        {
            //System.out.println(sensors.sensorValue());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
