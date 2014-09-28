package at.yawk.lolcode.parser;

import at.yawk.lolcode.instruction.Instruction;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author yawkat
 */
public abstract class SimpleBlockBuilder implements BlockBuilder {
    @Getter(AccessLevel.PROTECTED) private final List<Instruction> content = new ArrayList<>();

    @Override
    public void add(Instruction instruction) {
        content.add(instruction);
    }
}
