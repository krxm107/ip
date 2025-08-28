package brobot.brobotexceptions;

public final class SomeArgsLeftException extends BrobotCommandFormatException {
    private SomeArgsLeftException(final String mainMessage) {
        super(mainMessage);
    }

    public static SomeArgsLeftException fromCommandName(final String commandName) {
        return new SomeArgsLeftException(String.format("The '%s' command does not take in any arguments.", commandName));
    }
}
