package tests.threads2;

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
        System.out.println("Thread aa: val:" + counter++);
    }

    @Override
    public void run() {
        for (int i = aa; i < 10; i++) {
            task();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
