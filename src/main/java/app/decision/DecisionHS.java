package app.decision;

import constants.ChanName;

import java.util.HashSet;

/**
 * Created by mac on 25.07.2017.
 */
public class DecisionHS {
    private final HashSet<ChanName> sen;
    private final HashSet<ChanName> perf;

    public DecisionHS(HashSet<ChanName> in, HashSet<ChanName> iout) {
        this.sen = in;
        this.perf = iout;
    }

    public HashSet<ChanName> sen() {
        return sen;
    }

    public HashSet<ChanName> perf() {
        return perf;
    }
}