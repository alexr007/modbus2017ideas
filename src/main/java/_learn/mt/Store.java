package _learn.mt;

/**
 * Created by alexr on 20.01.2017.
 */
public class Store {

    private int count = 0;

    public synchronized void get() {
        while (count <1) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        count--;
        System.out.println("Покупатель купил 1 товар");
        System.out.println("Товаров на складе: " + count);
        notify();
    }

    public synchronized void put() {
        while (count >=3) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        count++;
        System.out.println("Производитель добавил 1 товар");
        System.out.println("Товаров на складе: " + count);
        notify();
    }
}

