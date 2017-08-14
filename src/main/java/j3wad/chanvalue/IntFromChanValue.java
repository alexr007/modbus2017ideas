package j3wad.chanvalue;

public class IntFromChanValue {
    private final ChanValue origin;

    public IntFromChanValue(ChanValue origin) {
        this.origin = origin;
    }

    public int get() {
        if (origin instanceof ChanValue.DO) {
            switch (((ChanValue.DO)origin).get()) {
                case ON: return 1;
                default: return 0;
            }
        } else {
            return ((ChanValue.A)origin).get();
        }
    }

    public static int from(ChanValue val) {
        return new IntFromChanValue(val).get();
    }

}
