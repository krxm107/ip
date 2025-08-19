package brobot.brobotexceptions;

public final class InvalidEventFormatException extends InvalidTaskFormatException {
    private InvalidEventFormatException (final String mainMessage) {
        super(mainMessage);
    }

    public static InvalidEventFormatException newInvalidEventFormatException() {
        final String mainMessage = "The format of an 'event' command is 'event taskName from startDate to endDate'.";
        return new InvalidEventFormatException(mainMessage);
    }
}
