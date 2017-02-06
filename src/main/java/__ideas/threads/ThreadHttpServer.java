package __ideas.threads;

import static java.lang.Thread.sleep;

public class ThreadHttpServer implements Runnable{
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++)
        {
            System.out.println("ThreadHttpServer: "+i);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
