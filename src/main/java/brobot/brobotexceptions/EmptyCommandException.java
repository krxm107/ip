package brobot.brobotexceptions;

/**
 * This exception is thrown if the user doesn't key in a command.
 */
public final class EmptyCommandException extends BrobotCommandFormatException {
    /**
     * Constructs the exception. Prompts user to key in a command.
     */
    public EmptyCommandException() {
        super("Please key in a command.");
    }
}
