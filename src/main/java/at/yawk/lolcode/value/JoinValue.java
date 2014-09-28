package at.yawk.lolcode.value;

import java.util.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author yawkat
 */
@Getter
@EqualsAndHashCode
public class JoinValue implements Val {
    private final List<Val> members;

    public JoinValue(List<Val> members) {
        this.members = Collections.unmodifiableList(new ArrayList<>(members));
    }

    public Val collapse() {
        if (members.size() == 1) {
            return members.get(0);
        }
        return this;
    }
}
