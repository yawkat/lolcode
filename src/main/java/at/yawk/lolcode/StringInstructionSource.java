package at.yawk.lolcode;

import java.util.Optional;

/**
 * @author yawkat
 */
public interface StringInstructionSource {
    Optional<String> next();
}
