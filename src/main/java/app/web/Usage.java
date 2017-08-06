package app.web;

import java.io.IOException;
import java.util.Date;
import java.util.SortedMap;

/**
 * Created by alexr on 01.05.2017.
 */
public interface Usage {

    /**
     * Add more usage in bytes.
     * @param date When did it happen
     * @param bytes How many bytes
     * @throws IOException If failsRaw
     */
    void add(Date date, long bytes) throws IOException;

    /**
     * Total, over the last ten days.
     * @return The total in bytes
     * @throws IOException If failsRaw
     */
    long total() throws IOException;

    /**
     * History.
     * @return Full usage history
     * @throws IOException If failsRaw
     */
    SortedMap<Date, Long> history() throws IOException;

}
