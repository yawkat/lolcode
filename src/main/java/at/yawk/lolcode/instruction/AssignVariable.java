package at.yawk.lolcode.instruction;

import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class AssignVariable implements Instruction {
    private final String name;
    private final Instruction value;
}
