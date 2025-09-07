package brobot;

public final class StringNewlineFormatter {
    private StringNewlineFormatter() {

    }

    public static String removeTrailingNewlines(final CharSequence initialString, final int newlineCount) {
        final int newlineLen = System.lineSeparator().length();
        return initialString.toString().substring(0, initialString.length() - newlineCount * newlineLen);
    }
}
