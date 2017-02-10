package _learn.th;

/**
 * Created by alexr on 10.02.2017.
 */
public class Thread3s implements Runnable {
    private final Object monitor = new Object();

    @Override
    public void run() {
        for (int i = 1; i < 10; i++) {
            synchronized (monitor) {
                System.out.println("Thread 3: val:"+i);
                try {
                    monitor.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                monitor.notify();
            }
        }
    }
}
