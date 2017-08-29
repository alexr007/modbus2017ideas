package tests;

import j4core.BIOcore;
import j4core.ChannelsSet;
import j4core.entity.chan.ModBusChannels;
import constants.ChanName;
import constants.DevName;
import _IDEAS_.entities.EnRelay;
import j4core.HashMapFrom;
import jssc.SerialPortException;
import j3wad.channels.WAD_Channel;
import j3wad.chanvalue.ChanValue;
import j3wad.chanvalue.TypeDO;
import org.javatuples.Pair;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

import java.io.Closeable;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ModBusChannelsTest {
    public void test0 (BIOcore core) {
        System.out.println(
            core.channels().get(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().get("M_VIBRO_FILTR")
                )
        );
        System.out.println(
            core.channels().get(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().get(new Pair<>(0x35,4))
                )
        );
        System.out.println(
            core.channels().get(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().get(0x35,4)
                )
        );
        // output should be similar
        System.out.println(
            core.channels().get(0x31,1).toString()
        );
        System.out.println(
            core.channels().get(new Pair(0x31,1)).toString()
        );
        System.out.println(
            core.channels().get("VALVE_1").toString()
        );
        System.out.println(
            core.channels().get(ChanName.VALVE_1).toString()
        );
        // output should be similar
        WAD_Channel c = core.channels().get(0x31, 1);
        System.out.println(
            core.channels().getName(c)
        );
        System.out.println(
            core.channels().getName(0x31, 1)
        );
        System.out.println(
            core.channels().getName(new Pair(0x31,1))
        );
    }

    public void test1 (BIOcore core) {
        HashMap<ChanName, ChanValue> map = new HashMap<>();
        map.put(ChanName.R1, ChanValue.DO(TypeDO.OFF));

        new ChannelsSet(core.channels()).write(
            new HashMapFrom<>(
                new AbstractMap.SimpleEntry<>(ChanName.R1, ChanValue.DO(TypeDO.OFF)),
                new AbstractMap.SimpleEntry<>(ChanName.R2, ChanValue.DO(TypeDO.OFF)),
                new AbstractMap.SimpleEntry<>(ChanName.R3, ChanValue.DO(TypeDO.ON)),
                new AbstractMap.SimpleEntry<>(ChanName.V10_1, ChanValue.A(5000)),
                new AbstractMap.SimpleEntry<>(ChanName.V10_2, ChanValue.A(6000)),
                new AbstractMap.SimpleEntry<>(ChanName.V10_3, ChanValue.A(7000))
            )
        );
        System.out.println(
            new ChannelsSet(core.channels()
            ).read(ChanName.R1,
                ChanName.R2,
                ChanName.R3,
                ChanName.R4
            )
        );
        System.out.println(map);
    }

    public void test2 (BIOcore core) {
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
        new ChannelsSet(core.channels()).write(wr);
    }

    public void test3 (BIOcore core) throws InvalidModBusFunction, SerialPortException, ImpossibleModificationException {
        System.out.println(
            core.dev(DevName.DOS1).summaryTxt()
        );

        core.chan(ChanName.R1).on();
        System.out.println(
            core.dev(DevName.DOS1).summaryTxt()
        );
        WAD_Channel ch = core.dev(DevName.DOS1).channel(0);

        System.out.println(
            new Xembler(core.dev(DevName.DOS1).summaryXml()).xml()
        );
    }

    /**
     * learn to Closeable in try() {}
     * @param core
     */
    public void test4(BIOcore core) {

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

    public void test5(BIOcore core) throws Exception {
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
