package Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DebugWriter {
    public static File file;
    public static FileOutputStream fs;
    public static OutputStreamWriter os;
    public static BufferedWriter writer;

    public DebugWriter(String filename) {
        file = new File(System.getProperty("user.dir") + filename + ".txt");
        try {
            fs = new FileOutputStream(file);
            os = new OutputStreamWriter(fs);
            writer = new BufferedWriter(os);
        } catch (IOException e) {
            System.err.println("Problem writing to the file");
        }
    }

    public static BufferedWriter getWriter() {
        return writer;
    }

    public static void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Problem closing the file");
        }
    }
}
