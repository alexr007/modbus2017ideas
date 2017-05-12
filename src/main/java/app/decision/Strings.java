package app.decision;

/**
 * Created by alexr on 24.04.2017.
 */
public class Strings {
    private final CharSequence[] origin;

    public Strings(CharSequence... origin) {
        this.origin = origin;
    }

    public CharSequence[] get() {
        return this.origin;
    }
}
