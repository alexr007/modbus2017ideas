package __ideas.test;

import __ideas.ranges.RandomFromRange;
import __ideas.ranges.RangeValues;
import __ideas.validator.ValidValues;
import __ideas.filters.ValueBuffer;
import __ideas.filters.ValueItem;

import java.util.*;

public class TestIdeas_OLD {

    public void test1()
    {
        RangeValues range1 = new RangeValues(1, 10);
        RangeValues range2 = new RangeValues(15, 25);

        ArrayList<RangeValues> list = new ArrayList();
        list.add(range1);
        list.add(range2);

        ValidValues vv = new ValidValues(list);
        System.out.println(vv.valid(15));
    }

    public void test2()
    {
        RangeValues range1 = new RangeValues(1, 10);
        RangeValues range2 = new RangeValues(15, 25);

        RangeValues[] r = {range1, range2};
        ValidValues vv = new ValidValues(
                new ArrayList<RangeValues>(
                        Arrays.asList(
                                r
                        )
                )
        );

        System.out.println(vv.valid(15));
    }

    public void test3()
    {
        RangeValues range1 = new RangeValues(1, 10);
        RangeValues range2 = new RangeValues(15, 25);

        RangeValues[] r = {range1, range2};
        ValidValues vv = new ValidValues(r);

        System.out.println(vv.valid(15));
    }

    public void test_buffer()
    {
        ValueBuffer buffer = new ValueBuffer(3);
        buffer.print();
        buffer.add(new ValueItem(1));
        buffer.print();


        buffer.add(new ValueItem(2));
        buffer.print();
        buffer.add(new ValueItem(3));
        buffer.print();
        buffer.add(new ValueItem(4));
        buffer.print();
        buffer.add(new ValueItem(5));
        buffer.print();
        buffer.add(new ValueItem(6));
        buffer.print();
    }

    public void test_random()
    {
        RandomFromRange rr = new RandomFromRange(new RangeValues(10, 13));
        for (int i = 0; i < 20; i++) {
            System.out.println(rr.value());
        }
    }

    public void test_COM2()
    {

/*
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        System.out.println(portList.hasMoreElements());

        while(portList.hasMoreElements()){
            System.out.println("Has more elements");

            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
                System.out.println(portId.getName());
            }
            else{
                System.out.println(portId.getName());
            }

        }
*/
    }

    public void test_COM()
    {
    }

    public static void main1(String[] args)
    {


        TestIdeas_OLD ti = new TestIdeas_OLD();
        ti.test_COM();
/*
        ti.test1();
        ti.test2();
        ti.test3();
*/

        /*
        ThreadSensors tSensors = new ThreadSensors(true);
        ThreadLogic tLogic = new ThreadLogic(tSensors);
        ThreadHttpServer tHttpServer = new ThreadHttpServer();
        ThreadConsole tConsole = new ThreadConsole();

        new Thread(tSensors).start();
        new Thread(tLogic).start();
        */
//        new Thread(tHttpServer).start();
//        new Thread(tConsole).start();



        System.out.println("main");
        /*
        AbstractSensor waterLow = new AbstractSensor(new InputPort(11));
        AbstractSensor waterHigh = new AbstractSensor(new InputPort(12));

        RangeMapped mapped = new RangeMapped(range1, range2);
         */

        //System.out.println(range1.contains(5));

        //RangeMapped mapped = new RangeMapped(0,10,0,25);

        /*
        for (int i = mapped.src().lowerEndpoint(); i <= mapped.src().upperEndpoint(); i++)
        {
            System.out.print("get: "+i);
            int to = mapped.map(i);
            System.out.println(" to: "+to);
        }
        */

    }

}
