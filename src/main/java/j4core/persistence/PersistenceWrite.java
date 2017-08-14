package j4core.persistence;

import constants.ChanName;
import j3wad.chanvalue.ChanValue;

public interface PersistenceWrite {
    void write(ChanName name, ChanValue value);
}
