package brobot.brobotexceptions;

public abstract class BrobotCheckedException extends Exception {
    BrobotCheckedException(final String fullMessage) {
        super(fullMessage);
    }
}
