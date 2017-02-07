package common.hardware;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorDigital {
    // cable !fail
    boolean ok();
    // cable fail
    boolean fail();
    // normal state
    boolean opened();
    // shorted state
    boolean closed();
}
