package brobot.brobotexceptions;

public final class InvalidTODOFormatException extends InvalidTaskFormatException {
    private InvalidTODOFormatException (final String mainMessage) {
        super(mainMessage);
    }

    public static InvalidTODOFormatException newInvalidTODOFormatException() {
        final String mainMessage = "The format of the 'todo' command is 'todo taskName'.";
        return new InvalidTODOFormatException(mainMessage);
    }
}
