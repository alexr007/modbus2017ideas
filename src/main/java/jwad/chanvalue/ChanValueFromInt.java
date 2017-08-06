package jwad.chanvalue;

import jbus.modbus.response.MappedTo;
import jwad.channels.WadAbstractChannel;

/**
 * This class used to map readed integer value from device
 * to Class corresponding Device type:
 */
public class ChanValueFromInt implements MappedTo<ChanValue>{
    private final WadAbstractChannel channel;

    public ChanValueFromInt(WadAbstractChannel channel) {
        this.channel = channel;
    }

    @Override
    public ChanValue map(int value) {
        switch (channel.type()) {
            case AIK:
            case AO:
            case AO6  : return new ChanValue.A(value);
            case DI:
            case DI14 : return new ChanValue.DI(value);
            case DOS  : return new ChanValue.DO(value);
            default : throw new IllegalArgumentException(String.format(
                "ChanValueFromInt channel type must be declared properly, found:%s",channel.type()));
        }
    }
}
