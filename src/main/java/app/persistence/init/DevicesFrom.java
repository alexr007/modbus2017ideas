package app.persistence.init;

import constants.DevName;
import jwad.modules.WadAbstractDevice;
import java.util.HashMap;

/**
 * Created by mac on 21.07.2017.
 */
public interface DevicesFrom {
    HashMap<DevName, WadAbstractDevice> hashMap() throws Exception;
}
