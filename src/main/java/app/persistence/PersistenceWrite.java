package app.persistence;

import constants.ChanName;
import jwad.chanvalue.ChanValue;

public interface PersistenceWrite {
    void write(ChanName name, ChanValue value);
}
