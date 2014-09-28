package at.yawk.lolcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * @author yawkat
 */
public class InstructionReader implements StringInstructionSource {
    private final Reader reader;

    private final Queue<String> instructionQueue = new ArrayDeque<>();

    private boolean inBlockComment = false;

    public InstructionReader(Reader reader) {
        if (!reader.markSupported()) {
            reader = new BufferedReader(reader);
        }
        this.reader = reader;
    }

    private String nextLine() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (true) {
            int nextI = reader.read();
            if (nextI == -1) {
                break;
            }
            char next = (char) nextI;
            if (next == '\n' || next == '\r') { // newline
                trim(builder);
                if (builder.length() > 0) {
                    break;
                }
            } else {
                builder.append(next);
            }
        }
        trim(builder);
        return builder.toString();
    }

    private static void trim(StringBuilder builder) {
        int start;
        for (start = 0; start < builder.length(); start++) {
            char c = builder.charAt(start);
            if (c != ' ' && c != '\t') {
                break;
            }
        }
        int end;
        for (end = builder.length(); end > 0; end--) {
            char c = builder.charAt(end - 1);
            if (c != ' ' && c != '\t') {
                break;
            }
        }
        builder.delete(0, start);
        if (end > start) {
            builder.setLength(end - start);
        }
    }

    public Optional<String> nextInstruction() throws IOException {
        while (true) {
            String prep = "";
            if (!instructionQueue.isEmpty()) {
                String queuedInstruction = instructionQueue.poll();
                if (instructionQueue.isEmpty() && queuedInstruction.endsWith("...")) {
                    prep = queuedInstruction.substring(0, queuedInstruction.length() - 3).trim();
                } else {
                    return Optional.of(queuedInstruction);
                }
            }

            String nextLine = prep + nextLine();
            if (nextLine.isEmpty()) {
                return Optional.empty();
            }
            if (inBlockComment) {
                if (nextLine.startsWith("TLDR")) {
                    inBlockComment = false;
                    nextLine = nextLine.substring(4).trim();
                    if (nextLine.isEmpty()) {
                        continue;
                    }
                } else {
                    continue;
                }
            }

            int p = 0;
            int i = 0;
            do {
                i = literalIndexOf(nextLine, ",", i);
                String component = nextLine.substring(p, i == -1 ? nextLine.length() : i).trim();
                boolean cancelAfter = false;
                if (component.startsWith("OBTW")) {
                    inBlockComment = true;
                    break;
                } else {
                    int commentIx = component.indexOf("BTW");
                    if (commentIx != -1) {
                        component = component.substring(0, commentIx).trim();
                        cancelAfter = true;
                    }
                }
                if (!component.isEmpty()) {
                    instructionQueue.offer(component);
                }
                if (cancelAfter) {
                    break;
                }
                i++;
                p = i;
            } while (i != 0); // EOL
        }
    }

    private static int literalIndexOf(CharSequence haystack, CharSequence needle, int start) {
        char currentLiteralDelimiter = 0;
        boolean escaped = false;
        for (int needlePos = 0, haystackPos = start; haystackPos < haystack.length(); haystackPos++) {
            char c = haystack.charAt(haystackPos);
            if (currentLiteralDelimiter == 0) {
                if (c == '"') {
                    currentLiteralDelimiter = c;
                }
            } else {
                if (!escaped && c == currentLiteralDelimiter) {
                    currentLiteralDelimiter = 0;
                }
                escaped = c == ':';
            }
            if (currentLiteralDelimiter == 0 && needle.charAt(needlePos) == c) {
                needlePos++;
                if (needlePos >= needle.length()) {
                    return haystackPos - needlePos + 1;
                }
            } else {
                needlePos = 0;
            }
        }
        return -1;
    }

    @Override
    public Optional<String> next() {
        try {
            return nextInstruction();
        } catch (IOException e) {
            throw new LolcodeException(e);
        }
    }
}
