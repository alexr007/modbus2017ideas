package jtools.filters;

import java.sql.Timestamp;

public class ValueItem {

    private final int val;
    private final Timestamp time;

    public ValueItem(int val) {
        this.val = val;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp time() {
        return time;
    }

    public int value() {
        return val;
    }

    @Override
    public String toString() {
        return "v:"+val
                //+" time:"+time
                ;
    }
}
