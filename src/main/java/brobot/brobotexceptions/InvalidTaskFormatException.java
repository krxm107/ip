package brobot.brobotexceptions;

public sealed abstract class InvalidTaskFormatException extends BrobotCommandFormatException
        permits InvalidTODOFormatException, InvalidDeadlineFormatException, InvalidEventFormatException {

    protected InvalidTaskFormatException (final String mainMessage) {
        super(mainMessage);
    }
}
