import _IDEAS_.decision.test.SortedSetTest;
import _IDEAS_.decision.test.rx.rxTest1;
import app.*;
import constants.DevName;
import j2bus.modbus.InvalidModBusFunction;
import j2bus.modbus.response.InvalidModBusResponse;
import j3wad.chanvalue.test.ChanValueTest;
import j3wad.modules.WAD_DOS_BUS;
import j3wad.modules.WadAbstractDevice;
import j4core.BIOcore;
import jssc.SerialPortException;

public class ScadaCore {
    public static void main(String[] args)  {
        if (args.length == 0) {
            new AppFindComPorts().run();
            //new _IDEAS_.jdesign.decision.core_test.MainTest().core_test();
            //new t1().run();
        } else
        if ((args.length == 2)&&(args[1].equals("core_test"))) {
            //new AppConsoleSpeedTest(args[0]).run();
        } else {
            //OOPStest.main(args);
            //SortedSetTest.test33();
            //new ChanValueTest().test3();
            BIOcore core = null;
            try {
                core = new BIOcore(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //new rxTest1().test0(core);
            try {
                WAD_DOS_BUS dos = core.dev(DevName.DOS1).cast();
                long start=System.nanoTime();
                dos.channel(1).on();
                int t = dos.temperature();
                long delta=System.nanoTime()-start;
                System.out.printf("Temp:%d\n",t);
                System.out.printf("Time:%dns\n",delta);
                System.out.printf("Time:%dms\n",delta/1000000);

            } catch (InvalidModBusFunction invalidModBusFunction) {
                invalidModBusFunction.printStackTrace();
            } catch (SerialPortException e) {
                e.printStackTrace();
            } catch (InvalidModBusResponse invalidModBusResponse) {
                invalidModBusResponse.printStackTrace();
            }
            //System.out.println(core.devListTriplet());


/*
            ChannelsSet cs = new ChannelsSet(j4core.channels(), EnumSet.allOf(ChanName.class));
            System.out.println( cs.getAllChannelFromSameDevice(ChanName.SM_AIR1) );
            System.out.println( cs.getAllChannelFromSameDevice(ChanName.SM_GATE_4) );
            System.out.println( cs.getDeviceSet() );
            System.out.println( cs.getMapDeviceChanCount() );
            System.out.println( cs.getMapDeviceChanList()  );
*/

/*
            System.out.println(j4core.devices().toString());
            System.out.println(j4core.channels().toString());
*/


/*
            new ModBusChannelsTest().test3(j4core);
*/
            //new ChanValueTest().test3();

/*
            WAD_Channel chan = j4core.chan(ChanName.S_A_CRUSH);
            System.out.println(
                j4core.channelMap().getAllChannelFromSameDevice(ChanName.S_A_CRUSH)
            );
            System.out.println(
                j4core.channelMap().getAllChannelFromSameDevice(ChanName.M_AIR1)
            );
*/


            //BIOcore j4core = new BIOcore(Dv.COM26);

/*
            new ArrayList<Runnable>() {{
                // создаем массив Runnable
                add(new AppConsole(j4core));
                //add(new AppDecision(j4core));
                add(new AppWebServer(j4core));

                // массив Thread
                ArrayList<Thread> th = new ArrayList<Thread>();
                // добавляем все Runnable -> Thread
                forEach(r -> th.add(new Thread(r)));
                th.forEach(tests.t -> tests.t.start());
                System.out.println("All threads started");
                th.forEach(tests.t -> {
                    try {
                        tests.t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                System.out.println("All threads finished");
            }};
*/
            core.finish();
        }
    }
    // todo timeout while send packed to module;
}
