package common.comport;

import jssc.SerialPortList;

/**
 * Created by alexr on 02.12.2016.
 */
public class COMPortList {

    private SerialPortList portList = new SerialPortList();;
    private boolean readed = false;
    private String[] list = null;

    public String[] values()
    {
        doRead();
        return list;
    }

    public int count()
    {
        doRead();
        return list.length;
    }

    public String toString() {
        doRead();
        String prefix = "";
        StringBuilder sb = new StringBuilder();
        sb.append(count());
        sb.append(":[");

        for (String s : values()) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(s);
        }

        sb.append("]");
        return sb.toString();

    }

    private void doRead()
    {
        if (!readed) {
            list = portList.getPortNames();
        }
    }

}
