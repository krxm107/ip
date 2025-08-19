package brobot.brobotexceptions;

public final class EmptyCommandException extends BrobotCommandException {
    public EmptyCommandException() {
        super("Please key in a command.");
    }
}
