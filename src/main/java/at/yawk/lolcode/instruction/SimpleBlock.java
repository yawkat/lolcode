package at.yawk.lolcode.instruction;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yawkat
 */
@RequiredArgsConstructor
@Getter
public abstract class SimpleBlock implements Instruction {
    private final List<Instruction> instructions;
}
