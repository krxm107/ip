package brobot.brobotexceptions;

public final class NoSuchCommandNameException extends BrobotCommandFormatException {
    private NoSuchCommandNameException (final String mainMessage) {
        super(mainMessage);
    }

    public static NoSuchCommandNameException newInstancefromCommandName (final String commandName) {
        return new NoSuchCommandNameException(String.format("There is no '%s' command.", commandName));
    }
}
