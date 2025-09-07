package brobot;

public final class NewlineFormatter {
    private NewlineFormatter() {

    }

    public static String removeTrailingNewlines(final CharSequence initialString, final int newlineCount) {
        final int newlineLen = System.lineSeparator().length();
        return initialString.toString().substring(0, initialString.length() - newlineCount * newlineLen);
    }
}
