package at.yawk.lolcode.instruction;

import at.yawk.lolcode.value.Val;
import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class Constant implements Instruction {
    private final Val value;
}
