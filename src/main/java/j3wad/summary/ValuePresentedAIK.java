package j3wad.summary;

/**
 * Created by alexr on 01.05.2017.
 */
public class ValuePresentedAIK implements ValuePresented {
    private final int origin;

    public ValuePresentedAIK(int origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return Integer.toString(origin);
    }
}
