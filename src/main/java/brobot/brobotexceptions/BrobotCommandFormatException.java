package brobot.brobotexceptions;

/**
 * Abstract base class for Brobot domain specific errors that happen when processing a command.
 * <p>
 * <pre>
 * These are Checked Exceptions. They must be caught and handled so that when users enter an invalid command,
 * they are notified of this error.
 * </pre>
 */
public abstract class BrobotCommandFormatException extends BrobotCheckedException implements Runnable {
    private static final String INVALID_INPUT_WARNING = "Sorry, this input is invalid!";
    private static final String ANOTHER_CHANCE_OFFERING = "Please enter a different input.\nProgram resumed.";

    private static final String getFullMessage(final String mainMessage) {
        return String.join("\n",
                            BrobotCommandFormatException.INVALID_INPUT_WARNING,
                            mainMessage,
                            BrobotCommandFormatException.ANOTHER_CHANCE_OFFERING);
    }

    /**
     * @param mainMessage the core message explaining the invalid command format
     *
     * The full {@code BrobotCommandFormatException} message will be formatted as:
     *
     * <pre>
     * Sorry, this input is invalid!
     * {mainMessage}
     * Please enter a different input.
     * Program resumed.
     * </pre>
     */
    BrobotCommandFormatException(final String mainMessage) {
        super(BrobotCommandFormatException.getFullMessage(mainMessage));
    }

    /**
     * Allows the message to be displayed to the user.
     */
    public final void run() {
        System.out.println(getMessage());
    }
}
