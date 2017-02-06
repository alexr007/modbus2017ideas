package _learn.learn;

public class DataManager {

    private static final Object monitor = new Object();

    public void sendData() {
        synchronized (monitor) {
            System.out.println("Waiting for data...");
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // continue execution and sending get
            System.out.println("Sending data...");
        }
    }

    public void prepareData() {
        synchronized (monitor) {
            System.out.println("Data prepared");
            monitor.notifyAll();
        }
    }
}