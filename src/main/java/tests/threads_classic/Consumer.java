package tests.threads_classic;

/**
 * Created by alexr on 20.01.2017.
 */
public class Consumer implements Runnable {
    private final Store store;

    public Consumer(Store st) {
        store = st;
    }


    public void run(){
        for (int i = 1; i < 6; i++) {
            store.get();
        }
    }
}
