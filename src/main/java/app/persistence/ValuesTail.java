package app.persistence;

import jwad.chanvalue.ChanValue;

import java.util.ArrayList;
import java.util.List;

/**
 * class to support buffer of values
 * size - this is the length
 * of the stored queue
 *
 * get(0) - newest value
 * get(size) - oldest value
 */
public class ValuesTail {
    private final int size;
    private final List<ValuesTailEntry> tail;

    /**
     * Ctor
     * @param size - size of list of values to store
     */
    public ValuesTail(int size) {
        this.size = size;
        this.tail = new ArrayList<>(size+1);
    }

    public int size() {
        return size;
    }

    public void add(ChanValue value){
        if (size>0) {
            // always add to begin of list
            tail.add(0, new ValuesTailEntry(value));
            // remove the last item
            if (tail.size() > size) {
                tail.remove(tail.size() - 1);
            }
        }
    }

    ValuesTailEntry get(int index) {
        return tail.get(index);
    }


    public List<ValuesTailEntry> list() {
        return tail;
    }
}
