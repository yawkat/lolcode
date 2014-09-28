package at.yawk.lolcode;

/**
 * @author yawkat
 */
public class LolcodeException extends Error {
    public LolcodeException() {
    }

    public LolcodeException(String message) {
        super(message);
    }

    public LolcodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LolcodeException(Throwable cause) {
        super(cause);
    }
}
