package app.persistence.init.chan.test;

import entities.EnRelay;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Runnable1 implements Runnable{
    private final CountDownLatch cdl;
    private final EnRelay r1;
    private final int sleep;
    private final int count;

    public Runnable1(CountDownLatch cdl, EnRelay r1, int sleep, int count) {
        this.cdl = cdl;
        this.r1 = r1;
        this.sleep = sleep;
        this.count = count;
    }

    @Override
    public void run() {
        IntStream.range(0,this.count)
            .forEach( value -> {
                    System.out.printf("Thread:%s, step:%d\n",Thread.currentThread().getName(),value);
                    try {
                        if (value % 2 == 0) {
                            r1.on();
                        } else {
                            r1.off();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(this.sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            );
        cdl.countDown();
    }
}
