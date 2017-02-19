package tests.threads1;

/**
 * Created by alexr on 20.01.2017.
 */
public class TestApp {
    public static void main(String[] args) {
        Store store=new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}


