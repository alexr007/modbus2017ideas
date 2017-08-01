package app.web;

import jwad.WadDevType;

/**
 * Created by alexr on 02.05.2017.
 */
public class ValueValidated {
    private final WadDevType type;

    public ValueValidated(WadDevType type) {
        this.type = type;
    }

    public int value(String value) {
        int val;
        switch (type) {
            case DOS: val = new ValueDOS(value).val();
                break;
            case AO: val = new ValueAO(value).val();
                break;
            case AO6: val = new ValueAO(value).val();
                break;
            default: throw new IllegalArgumentException(String.format("Can'tests.t set channel value for device type",type));
        }
        return val;
    }
}
