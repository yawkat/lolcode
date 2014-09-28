package at.yawk.lolcode.value;

import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class Primitive<T> implements Val {
    private final T value;
}
