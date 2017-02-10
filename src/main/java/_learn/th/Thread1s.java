package _learn.th;

import static java.lang.Thread.sleep;

/**
 * Created by alexr on 10.02.2017.
 */
public class Thread1s implements Runnable {
    private final Locker locker;
    private int counter = 0;

    public Thread1s(Locker locker) {
        this.locker = locker;
    }

    private void task() {
        System.out.println("Thread 1: val:" + counter++);
    }

    @Override
    public void run() {
        for (int i = 1; i < 10; i++) {
            task();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
