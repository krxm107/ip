package brobot.brobotexceptions;

public sealed abstract class InvalidTaskFormatException extends BrobotCommandException
        permits InvalidTODOFormatException, InvalidDeadlineFormatException, InvalidEventFormatException {

    protected InvalidTaskFormatException (final String mainMessage) {
        super(mainMessage);
    }
}
