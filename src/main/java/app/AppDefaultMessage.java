package app;

import com.google.common.base.Joiner;

/**
 * Created by alexr on 21.04.2017.
 */
public class AppDefaultMessage {
    @Override
    public String toString() {
        return Joiner.on("\n").join(
            "Usage:",
            "--list to list available COM ports (default behavior)",
            "--web-port=NN to specify the web port, default 8001",
            "--term-port=NN to specify the terminal port, no default",
            "--test to real test com port speed"
        );
    }
}
