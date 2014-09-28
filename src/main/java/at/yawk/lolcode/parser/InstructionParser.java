package at.yawk.lolcode.parser;

import at.yawk.lolcode.instruction.Instruction;
import java.util.Optional;

/**
 * @author yawkat
 */
public interface InstructionParser {
    Optional<Instruction> parse(String literal);
}
