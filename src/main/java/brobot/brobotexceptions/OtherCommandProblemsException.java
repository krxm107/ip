package brobot.brobotexceptions;

/**
 * This class accounts for other exceptions that are thrown due to invalid user input.
 */
public final class OtherCommandProblemsException extends BrobotCommandFormatException {
    /**
     * Exception thrown when the user enters a command that does not match
     * any of the anticipated commands.
     *
     * <p>
     * This acts as a general catch-all for unexpected or invalid inputs
     * beyond the explicitly handled cases. Thus, no specific exception main message
     * can be made, so it was left as an empty String.
     * </p>
     */
    public OtherCommandProblemsException() {
        super("");
    }
}
