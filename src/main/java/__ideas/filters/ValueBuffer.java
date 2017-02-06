package __ideas.filters;

import java.util.concurrent.LinkedBlockingQueue;

public class ValueBuffer {

    private final int capacity;
    private LinkedBlockingQueue queue;

    public ValueBuffer(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue(capacity);
    }

    public int capacity() {
        return capacity;
    }

    public int count() {
        return queue.size();
    }

    public boolean full()
    {
        return queue.remainingCapacity() == 0;
    }

    public LinkedBlockingQueue getQueue() {
        return queue;
    }

    public void add(ValueItem item)
    {
        if (queue.remainingCapacity() == 0)
            queue.poll();

        queue.add(item);
    }

    public  void print()
    {
        System.out.println(queue.toString()+" full:"+full());
    }

}
