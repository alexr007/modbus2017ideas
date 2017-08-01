package jwad.summary;

import jwad.chanvalue.TypeDI;

/**
 * Created by alexr on 01.05.2017.
 */
public class ValuePresentedDI implements ValuePresented {
    private final int origin;
    private final int fail;
    private final int I_OPENED = 0;
    private final int I_CLOSED = 1;
    private final int I_FAIL = 1;

    public ValuePresentedDI(int origin, int fail) {
        this.origin = origin;
        this.fail = fail;
    }

    @Override
    public String toString() {
        return (
            fail==I_FAIL ? TypeDI.FAIL :
                (origin==I_CLOSED ? TypeDI.CLOSED : TypeDI.OPENED)
            ).toString();
    }
}
