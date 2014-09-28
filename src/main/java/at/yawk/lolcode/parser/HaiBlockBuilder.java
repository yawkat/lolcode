package at.yawk.lolcode.parser;

import at.yawk.lolcode.LolcodeException;
import at.yawk.lolcode.instruction.HaiBlock;
import at.yawk.lolcode.instruction.Instruction;
import java.util.Optional;

/**
 * @author yawkat
 */
public class HaiBlockBuilder extends SimpleBlockBuilder {
    private String version = null;

    @Override
    public boolean add(String instruction) {
        if (version == null) {
            if (!instruction.startsWith("HAI ")) {
                throw new LolcodeException("First instruction must be HAI <version>");
            }
            version = instruction.substring(4);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Instruction> finish(String instruction) {
        if (instruction.equals("KTHXBYE")) {
            return Optional.of(new HaiBlock(getContent(), version));
        }
        return Optional.empty();
    }
}
