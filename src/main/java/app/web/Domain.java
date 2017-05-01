package app.web;

import java.io.IOException;

/**
 * Created by alexr on 01.05.2017.
 */
public interface Domain {

    /**
     * Owner of it.
     * @return The owner's GitHub handle
     * @throws IOException If fails
     */
    String owner() throws IOException;

    /**
     * Name.
     * @return The name
     * @throws IOException If fails
     */
    String name() throws IOException;

    /**
     * Delete it.
     * @throws IOException If fails
     */
    void delete() throws IOException;

    /**
     * Usage.
     * @return Usage
     * @throws IOException If fails
     */
    Usage usage() throws IOException;

}
