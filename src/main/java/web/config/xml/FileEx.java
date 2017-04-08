package web.config.xml;

import com.google.common.io.Files;
//import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

/**
 * Created by alexr on 14.01.2017.
 */
public class FileEx {
    public void test() throws IOException {
        throw new IOException();
/*
        File file1 = new File("dummy2.xml");
        FileUtils.writeStringToFile(file1, "test2");

        final File file = new File(Files.createTempDir(), "dummy.xml");
        FileUtils.writeStringToFile(file, "__ideas/test");
*/
    }
}
