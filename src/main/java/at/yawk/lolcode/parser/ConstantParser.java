package at.yawk.lolcode.parser;

import at.yawk.lolcode.LolcodeException;
import at.yawk.lolcode.instruction.Instruction;
import at.yawk.lolcode.value.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author yawkat
 */
public class ConstantParser implements InstructionParser {
    private static final Pattern INTEGER = Pattern.compile("-?[0-9]+");
    private static final Pattern FLOAT = Pattern.compile("-?[0-9]*(\\.[0-9]|[0-9]\\.)[0-9]*");
    private static final Pattern STRING = Pattern.compile("\"(:\"|[^\"])*\"");

    @Override
    public Optional<Instruction> parse(String literal) {

    }

    private Val parseVal(String literal) {
        if (literal.equals("WIN")) {
            return new BooleanValue(true);
        }
        if (literal.equals("FAIL")) {
            return new BooleanValue(false);
        }
        if (INTEGER.matcher(literal).matches()) {
            return new IntegerValue(Long.parseLong(literal));
        }
        if (FLOAT.matcher(literal).matches()) {
            return new FloatValue(Double.parseDouble(literal));
        }
        if (STRING.matcher(literal).matches()) {
            List<Val> parts = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < literal.length() - 1; i++) {
                char c = literal.charAt(i);
                if (c == ':') {
                    if (literal.length() <= i + 2) {
                        throw new LolcodeException("Escape character at end of string");
                    }
                    char next = literal.charAt(i + 1);
                    if (next == ':') {
                        builder.append(c);
                    } else if (next == ')') {
                        builder.append('\n');
                    } else if (next == '>') {
                        builder.append('\t');
                    } else if (next == 'o') {
                        builder.append('\7');
                    } else if (next == '"') {
                        builder.append('"');
                    } else if (next == '(') {
                        int end = literal.indexOf(')', i + 2);
                        if (end == -1) {
                            throw new LolcodeException("Unterminated hex entity");
                        }
                        String hexSeq = literal.substring(i + 2, end);
                        try {
                            builder.appendCodePoint(Integer.parseInt(hexSeq, 16));
                        } catch (NumberFormatException e) {
                            throw new LolcodeException("Invalid hex entity", e);
                        }
                    } else if (next == '[') {
                        int end = literal.indexOf(']', i + 2);
                        if (end == -1) {
                            throw new LolcodeException("Unterminated named unicode entity");
                        }
                        String name = literal.substring(i + 2, end);
                        // TODO
                        throw new UnsupportedOperationException("Named unicode entities are not implemented yet!");
                    } else if (next == '{') {
                        int end = literal.indexOf('}', i + 2);
                        if (end == -1) {
                            throw new LolcodeException("Unterminated reference");
                        }
                        String ref = literal.substring(i + 2, end);
                        if (builder.length() > 0) {
                            parts.add(new StringValue(builder.toString()));
                            builder.setLength(0);
                        }
                        parts.add(new Reference(ref));
                    } else {
                        throw new LolcodeException("Unknown escape sequence: " + next);
                    }
                    i++;
                } else {
                    builder.append(c);
                }
            }
            if (builder.length() > 0 || parts.isEmpty()) {
                parts.add(new StringValue(builder.toString()));
            }
            return new JoinValue(parts).collapse();
        }
        throw new LolcodeException("Unrecognized type");
    }
}
