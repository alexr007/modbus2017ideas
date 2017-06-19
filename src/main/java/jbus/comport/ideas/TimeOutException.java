package jbus.comport.ideas;

/**
 * Created by mac on 19.06.2017.
 */
import java.io.IOException;

public class TimeOutException extends IOException {
    public TimeOutException(String message) {
        super(message);
    }
}

