package brobot.brobotexceptions;

public final class MissingNumberException extends BrobotCommandFormatException {
    private MissingNumberException (final String message) {
        super(message);
    }

    public static MissingNumberException fromCommandName (final String commandName) {
        final String mainMessage = String.format("The format of the '%s' command is '%s taskNumber'.", commandName);
        return new MissingNumberException(mainMessage);
    }
}
