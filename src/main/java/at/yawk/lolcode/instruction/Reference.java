package at.yawk.lolcode.instruction;

import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class Reference implements Instruction {
    private final String reference;
}
