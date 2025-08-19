package brobot.brobotexceptions;

public final class InvalidDeadlineFormatException extends InvalidTaskFormatException {
    private InvalidDeadlineFormatException (final String mainMessage) {
        super(mainMessage);
    }

    public static InvalidDeadlineFormatException newInvalidDeadlineFormatException() {
        final String mainMessage = "The format of a 'deadline' command is 'deadline taskName by dueDate'.";
        return new InvalidDeadlineFormatException(mainMessage);
    }
}
