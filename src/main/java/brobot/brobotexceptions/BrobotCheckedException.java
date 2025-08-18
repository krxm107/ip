package brobot.brobotexceptions;

public abstract class BrobotCheckedException extends Exception {
    protected BrobotCheckedException (final String fullMessage) {
        super(fullMessage);
    }
}
