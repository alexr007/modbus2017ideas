package common.device;

import common.IntAsHex;
import common.modbus.device.DeviceProperties;

/**
 * Created by alexr on 15.01.2017.
 */
public class Device__ {
    // ModBus ID
    private final int id;
    // name
    private final CharSequence name;
    // properties
    private final DeviceProperties properties;
    // builder description
    private final CharSequence description;
    // builder comment
    private final CharSequence comment;

    public Device__(int id, CharSequence name, DeviceProperties properties) {
        this(id, name, properties, "", "");
    }
    public Device__(int id, CharSequence name, DeviceProperties properties, CharSequence description) {
        this(id, name, properties, description, "");
    }

    public Device__(int id, CharSequence name, DeviceProperties properties, CharSequence description, CharSequence comment) {
        this.id = id;
        this.name = name;
        this.properties = properties;
        this.description = description;
        this.comment = comment;
    }

    public int id() {
        return this.id;
    }

    public CharSequence name() {
        return name;
    }

    public DeviceProperties properties() {
        return this.properties;
    }

    public CharSequence description() {
        return this.description;
    }

    public CharSequence comment() {
        return this.comment;
    }

    @Override
    public String toString() {
        return
            "Device__ id:"+new IntAsHex(id)+" "+
            "name:"+name+" "+
            "properties:"+properties+ " "+
            (description.equals("") ? "" : "description:"+description+" ")+
            (comment.equals("") ? "" : "comment:"+comment+" ");
    }
}
