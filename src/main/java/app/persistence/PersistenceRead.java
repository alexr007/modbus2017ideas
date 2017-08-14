package app.persistence;

import constants.ChanName;

public interface PersistenceRead {
    PersistenceEntry read(ChanName name);
}
