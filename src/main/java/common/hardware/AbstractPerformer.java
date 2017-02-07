package common.hardware;

import common.hardware.port.DRPort;

/**
 * Created by alexr on 07.02.2017.
 *
 *
 */

public abstract class AbstractPerformer {
    protected final DRPort port;

    public AbstractPerformer(DRPort port) {
        this.port = port;
    }
}
