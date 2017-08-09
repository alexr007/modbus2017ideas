package app.persistence.init.chan.test;

import app.persistence.BIOcore;
import app.persistence.init.chan.ChanSet;
import app.persistence.init.chan.ModBusChannels;
import constants.ChanName;
import entities.EnRelay;
import jbase.HashMapBuilder;
import jwad.chanvalue.ChanValue;
import jwad.chanvalue.TypeDO;

import java.io.Closeable;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ModBusChannelsTest {
    public void test1 (BIOcore core) {
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


        HashMap<ChanName, ChanValue> map = new HashMap<>();
        map.put(ChanName.R1, ChanValue.DO(TypeDO.OFF));


        new ChanSet(core.channels()).write(
            new HashMapBuilder<>(
                new AbstractMap.SimpleEntry<>(ChanName.R1, ChanValue.DO(TypeDO.OFF)),
                new AbstractMap.SimpleEntry<>(ChanName.R2, ChanValue.DO(TypeDO.OFF)),
                new AbstractMap.SimpleEntry<>(ChanName.R3, ChanValue.DO(TypeDO.ON)),
                new AbstractMap.SimpleEntry<>(ChanName.V10_1, ChanValue.A(5000)),
                new AbstractMap.SimpleEntry<>(ChanName.V10_2, ChanValue.A(6000)),
                new AbstractMap.SimpleEntry<>(ChanName.V10_3, ChanValue.A(7000))
            )
        );
        System.out.println(
            new ChanSet(core.channels()
            ).read(                ChanName.R1,
                ChanName.R2,
                ChanName.R3,
                ChanName.R4
            )
        );


/*
        System.out.println(map);
*/





/*
        System.out.printf("---------");
        System.out.println(
//            new ChanSet(core.channels(), EnumSet.range(ChanName.R1,ChanName.R8)).values()
//              new ChanSet(core.channels(), EnumSet.range(ChanName.V10_1,ChanName.V10_3)).values()
            new ChanSet(core.channels(), EnumSet.range(ChanName.R1,ChanName.V10_3)).values()
        );
*/
/*
        System.out.println(
            core.dev(DevName.DOS1).summaryTxt()
        );
*/
/*
        core.chan(ChanName.R1).on();
        System.out.println(
            core.dev(DevName.DOS1).summaryTxt()
        );
        WAD_Channel ch = core.dev(DevName.DOS1).channel(0);
*/

/*
        EnumMap<ChanName, ChanValue> wr = new EnumMap<>(ChanName.class);
        wr.put(ChanName.R1, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R2, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R3, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R4, ChanValue.DO(TypeDO.ON));
        wr.put(ChanName.R5, ChanValue.DO(TypeDO.OFF));
        wr.put(ChanName.R6, ChanValue.DO(TypeDO.OFF));
        wr.put(ChanName.R7, ChanValue.DO(TypeDO.OFF));
        wr.put(ChanName.R8, ChanValue.DO(TypeDO.OFF));
        wr.put(ChanName.V10_1, ChanValue.A(10000));
        wr.put(ChanName.V10_2, ChanValue.A(20000));
        wr.put(ChanName.V10_3, ChanValue.A(30000));
        //wr.put(ChanName.DC1, ChanValue.DI(TypeDI.OPENED));
        new ChanSet(core.channels()).write(wr);
*/
/*
        new ChanSet(core.channels(), wr).write(wr);
        new ChanSet(core.channels(), EnumSet.range(ChanName.R1,ChanName.R1)).write(wr);
*/
/*
        ch.set(new iface[]{1,0,1,0,1,0,1,0});
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
            new iface[]{
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
            .channel(0).set(new iface[]{
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

    /**
     * learn to Closeable in try() {}
     * @param core
     */
    public void test2(BIOcore core) {

        class Permit implements Closeable {
            private final BIOcore core1;

            Permit(BIOcore core1) {
                this.core1 = core1;
            }

            @Override
            public void close() {
                System.out.println("Closeable implemented");;
                core1.finish();
            }

            public Permit go() {
                return this;
            }
        }

        System.out.println("begin");
        try (
            Permit p = new Permit(core).go();
        ) {
            throw new IllegalArgumentException("EX");
        }

    }

    public void test3(BIOcore core) throws Exception {
        ModBusChannels channels = core.channels();
        EnRelay r1 = new EnRelay(channels.get(ChanName.R1));
        EnRelay r2 = new EnRelay(channels.get(ChanName.R2));
        EnRelay r3 = new EnRelay(channels.get(ChanName.R3));
        EnRelay r4 = new EnRelay(channels.get(ChanName.R4));

        r1.off();
        r2.off();
        r3.off();
        r4.off();

        System.out.println("Thread starting");
        CountDownLatch cdl =new CountDownLatch(2);
        new Thread(new Runnable1(cdl,r1,500, 20)).start();
        new Thread(new Runnable1(cdl,r2,1000, 20)).start();
        System.out.println("waiting for thread finish");
        cdl.await();
        System.out.println("Finished. Done");

    }
}
