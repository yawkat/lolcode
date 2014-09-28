package at.yawk.lolcode.instruction;

import at.yawk.lolcode.value.Val;
import java.util.Optional;
import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class DefineVariable implements Instruction {
    private final String name;
    private final Optional<Val> initialValue;
}
