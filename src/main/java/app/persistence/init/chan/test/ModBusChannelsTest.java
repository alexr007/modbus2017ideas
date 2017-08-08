package app.persistence.init.chan.test;

import app.persistence.BIOcore;
import app.persistence.init.chan.ChanSet;
import constants.ChanName;
import constants.DevName;
import jbus.modbus.InvalidModBusFunction;
import jssc.SerialPortException;
import jwad.channels.WAD_Channel;
import jwad.chanvalue.ChanValue;
import jwad.chanvalue.IntFromChanValue;
import jwad.chanvalue.TypeDI;
import jwad.chanvalue.TypeDO;
import org.javatuples.Pair;
import org.takes.rs.xe.XeDirectives;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

import java.util.*;

import static jwad.chanvalue.TypeChan.DO;
import static jwad.chanvalue.TypeDO.*;

public class ModBusChannelsTest {
    public void test1 (BIOcore core) throws ImpossibleModificationException, InvalidModBusFunction, SerialPortException {
        // output should be similar
/*
        System.out.println(
            core.channels().bytes(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().bytes("M_VIBRO_FILTR")
                )
        );
        System.out.println(
            core.channels().bytes(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().bytes(new Pair<>(0x35,4))
                )
        );
        System.out.println(
            core.channels().bytes(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().bytes(0x35,4)
                )
        );
        // output should be similar
        System.out.println(
            core.channels().bytes(0x31,1).toString()
        );
        System.out.println(
            core.channels().bytes(new Pair(0x31,1)).toString()
        );
        System.out.println(
            core.channels().bytes("VALVE_1").toString()
        );
        System.out.println(
            core.channels().bytes(ChanName.VALVE_1).toString()
        );
        // output should be similar
        WAD_Channel c = core.channels().bytes(0x31, 1);
        System.out.println(
            core.channels().getName(c)
        );
        System.out.println(
            core.channels().getName(0x31, 1)
        );
        System.out.println(
            core.channels().getName(new Pair(0x31,1))
        );
*/
        System.out.println("---------");
        System.out.println(
//            new ChanSet(core.channels(), EnumSet.range(ChanName.R1,ChanName.R8)).values()
//              new ChanSet(core.channels(), EnumSet.range(ChanName.V10_1,ChanName.V10_3)).values()
            new ChanSet(core.channels(), EnumSet.range(ChanName.R1,ChanName.R8)).values()
        );
        System.out.println(
            core.dev(DevName.DOS1).summaryTxt()
        );
/*
        core.chan(ChanName.R1).on();
        System.out.println(
            core.dev(DevName.DOS1).summaryTxt()
        );
        WAD_Channel ch = core.dev(DevName.DOS1).channel(0);
*/

        EnumMap<ChanName, ChanValue> wr = new EnumMap<>(ChanName.class);
        wr.put(ChanName.R1, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R2, ChanValue.DO(TypeDO.OFF));
        wr.put(ChanName.R3, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R4, ChanValue.DO(TypeDO.OFF));
        wr.put(ChanName.R5, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R6, ChanValue.DO(TypeDO.OFF));
        wr.put(ChanName.R7, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R8, ChanValue.DO(TypeDO.OFF));
        //wr.put(ChanName.DC1, ChanValue.DI(TypeDI.OPENED));
        new ChanSet(core.channels()).write(wr);
/*
        new ChanSet(core.channels(), wr).write(wr);
        new ChanSet(core.channels(), EnumSet.range(ChanName.R1,ChanName.R1)).write(wr);
*/
/*
        ch.set(new int[]{1,0,1,0,1,0,1,0});
        ch.set(
                Arrays.asList(
                    ChanValue.DO(ON),
                    ChanValue.DO(OFF),
                    ChanValue.DO(ON),
                    ChanValue.DO(OFF),
                    ChanValue.DO(ON),
                    ChanValue.DO(OFF),
                    ChanValue.DO(ON),
                    ChanValue.DO(OFF)
                )
        );
        ch.set(
            new int[]{
                IntFromChanValue.from(ChanValue.DO(ON)),
                IntFromChanValue.from(ChanValue.DO(ON)),
                IntFromChanValue.from(ChanValue.DO(ON)),
                IntFromChanValue.from(ChanValue.DO(ON)),
                IntFromChanValue.from(ChanValue.DO(OFF)),
                IntFromChanValue.from(ChanValue.DO(OFF)),
                IntFromChanValue.from(ChanValue.DO(OFF)),
                IntFromChanValue.from(ChanValue.DO(OFF))
                }
        );
*/
/*
        core.dev(DevName.AO1)
            .channel(0).set(new int[]{
            100,200,300,400,500,600
        });
        System.out.println(
            core.dev(DevName.AO1).summaryTxt()
        );
*/



/*
        System.out.println(
            new Xembler(core.dev(DevName.DOS1).summaryXml()).xml()
        );
*/
    }

}
