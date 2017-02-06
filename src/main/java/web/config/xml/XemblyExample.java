package web.config.xml;

import org.xembly.Directives;
import org.xembly.Xembler;

/**
 * Created by alexr on 13.01.2017.
 */
public class XemblyExample {
    public String test() throws Exception {
        final String prefix="WAD-";
        final String suffix="-BUS";
        final String[][] names = {
                {"AIK","in","a","4"},
                {"AIK12","in","a","12"},
                {"AO","out","a","4"},
                {"AO16","out","a","16"},
                {"DI","in","d","8"},
                {"DI14","in","d","14"},
                {"DOS","out","r","8"},
                {"DOS12","out","r","12"},
        };
        Directives dirs = new Directives().add("modules");
        for (String[] line : names) {
            dirs.add("module");
            dirs.add("name").set(prefix + line[0] + suffix).up();
            dirs.add("inout").set(line[1]).up();
            dirs.add("adr").set(line[2]).up();
            dirs.add("count").set(line[3]).up();
            dirs.add("description").set("").up();
            dirs.add("comment").set("").up();
            dirs.up();
        }

        return new Xembler(dirs).xml();
    }
}
