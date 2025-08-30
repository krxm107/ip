package brobot.brobotexceptions;

/**
 * This exception is thrown iff the command entered by the user is not supposed to take any arguments,
 * but the user supplied arguments anyway.
 */
public final class SomeArgsLeftException extends BrobotCommandFormatException {
    private SomeArgsLeftException(final String mainMessage) {
        super(mainMessage);
    }

    /**
     * Factory constructor
     *
     * @param commandName
     *     The command name that doesn't take in any arguments.
     *
     * @return
     *     A new instance as created by this factory constructor.
     */
    public static SomeArgsLeftException fromCommandName(final String commandName) {
        return new SomeArgsLeftException(
            String.format("The '%s' command does not take in any arguments.", commandName));
    }
}
