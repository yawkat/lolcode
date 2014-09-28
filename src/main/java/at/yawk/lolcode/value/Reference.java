package at.yawk.lolcode.value;

import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class Reference implements Val {
    private final String referenceName;
}
