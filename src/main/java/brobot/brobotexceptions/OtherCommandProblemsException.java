package brobot.brobotexceptions;

/**
 * This class accounts for other exceptions that are thrown due to invalid user input.
 */
public final class OtherCommandProblemsException extends BrobotCommandFormatException {
    /**
     * A main message is not required for this command.
     * <p>
     * The full message is as follows.
     * <pre>
     * Sorry, this input is invalid!
     * Please enter a different input.
     * Program resumed.
     * </pre>
     */
    public OtherCommandProblemsException() {
        super("");
    }
}
