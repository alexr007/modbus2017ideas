package app.persistence.init.chan;

import constants.ChanName;

public class ChanEntry {
    private final ChanName name;
    private final int chanId;
    private final int tailSize;
    private final static int DEFAULT_TAIL_SIZE = 1;

    public ChanEntry(ChanName name, int chanId) {
        this(name, chanId, DEFAULT_TAIL_SIZE);
    }

    public ChanEntry(ChanName name, int chanId, int tailSize) {
        this.name = name;
        this.chanId = chanId;
        this.tailSize = tailSize;
    }

    public ChanName name() {
        return name;
    }

    public int chanId() {
        return chanId;
    }

    public int tailSize() {
        return tailSize;
    }

    @Override
    public String toString() {
        return String.format("chan.name:-13%s, chan.id:%-2d, tail.size:%-2d",
            name, chanId, tailSize
        );
    }
}
