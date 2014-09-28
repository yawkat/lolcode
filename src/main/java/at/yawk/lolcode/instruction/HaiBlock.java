package at.yawk.lolcode.instruction;

import java.util.List;
import lombok.Getter;

/**
 * @author yawkat
 */
@Getter
public class HaiBlock extends SimpleBlock {
    private final String version;

    public HaiBlock(List<Instruction> instructions, String version) {
        super(instructions);
        this.version = version;
    }
}
