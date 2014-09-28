package at.yawk.lolcode;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author yawkat
 */
public class Test {
    public static void main(String[] args) throws IOException {
        InstructionReader reader = new InstructionReader(new FileReader("test.lc"));
        new Compiler().compile(reader);
    }
}
