package at.yawk.lolcode;

import at.yawk.lolcode.instruction.Instruction;
import at.yawk.lolcode.parser.BlockBuilder;
import at.yawk.lolcode.parser.HaiBlockBuilder;
import at.yawk.lolcode.parser.InstructionParser;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author yawkat
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Compiler {
    private final List<InstructionParser> parsers;

    public Instruction compile(StringInstructionSource source) {
        return compile(source, new HaiBlockBuilder());
    }

    public Instruction compile(StringInstructionSource source, BlockBuilder target) {
        while (true) {
            Optional<String> line = source.next();
            if (!line.isPresent()) {
                throw new LolcodeException("Incomplete block");
            }
            String literal = line.get();
            Optional<Instruction> finish = target.finish(literal);
            if (finish.isPresent()) {
                return finish.get();
            }
            if (target.add(literal)) {
                continue;
            }
            Instruction next = compile(literal);
            target.add(next);
        }
    }

    public Instruction compile(String literal) {
        Instruction next = null;
        for (InstructionParser parser : parsers) {
            Optional<Instruction> parsed = parser.parse(literal);
            if (parsed.isPresent()) {
                next = parsed.get();
                break;
            }
        }
        if (next == null) {
            throw new LolcodeException("Unknown instruction '" + literal + "'");
        }
        return next;
    }
}
