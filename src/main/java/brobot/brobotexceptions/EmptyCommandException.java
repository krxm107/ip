package brobot.brobotexceptions;

public final class EmptyCommandException extends BrobotCommandFormatException {
    public EmptyCommandException() {
        super("Please key in a command.");
    }
}
