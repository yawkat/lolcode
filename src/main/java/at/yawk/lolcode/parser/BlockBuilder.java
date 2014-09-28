package at.yawk.lolcode.parser;

import at.yawk.lolcode.instruction.Instruction;
import java.util.Optional;

/**
 * @author yawkat
 */
public interface BlockBuilder {
    default boolean add(String instruction) {
        return false;
    }

    void add(Instruction instruction);

    Optional<Instruction> finish(String instruction);
}
