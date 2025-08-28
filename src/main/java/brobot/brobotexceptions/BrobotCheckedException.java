package brobot.brobotexceptions;

/**
 * Abstract Base Class for Brobot domain specific Checked Exceptions.
 */
public abstract class BrobotCheckedException extends Exception {
    /**
     * Constructs a new instance of the BrobotCheckedException.
     *
     * @param fullMessage
     * <pre>
     * The fullMessage is the Exception message that will be displayed to the user
     * when such a checked Exception is caught.
     * </pre>
     */
    BrobotCheckedException(final String fullMessage) {
        super(fullMessage);
    }
}
