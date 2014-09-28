package at.yawk.lolcode;

import at.yawk.lolcode.parser.InstructionParser;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yawkat
 */
public class CompilerBuilder {
    private final List<InstructionParser> parsers = new ArrayList<>();

    public CompilerBuilder append(InstructionParser parser) {
        parsers.add(parser);
        return this;
    }

    public Compiler build() {
        return new Compiler(new ArrayList<>(parsers));
    }
}
