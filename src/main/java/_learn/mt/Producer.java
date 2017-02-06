package _learn.mt;

/**
 * Created by alexr on 22.01.2017.
 */
public class Producer implements Runnable {
    private final Store store;

    public Producer(Store st) {
        store = st;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++)
            store.put();
    }
}








