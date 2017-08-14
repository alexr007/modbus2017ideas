package _IDEAS_.jtools;

/**
 * Created by alexr on 27.04.2017.
 */
public class Timed {
    private final long begin = System.currentTimeMillis();

    public long get() {
        return
            System.currentTimeMillis() - begin;
    }

    public void print() {
        System.out.println(
            String.format("Time elapsed: %dms\n", get())
        );
    }
}
