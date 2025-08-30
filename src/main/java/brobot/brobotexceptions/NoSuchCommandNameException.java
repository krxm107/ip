package brobot.brobotexceptions;

/**
 * This exception is thrown if the user entered an invalid command name.
 */
public final class NoSuchCommandNameException extends BrobotCommandFormatException {
    private NoSuchCommandNameException(final String mainMessage) {
        super(mainMessage);
    }

    /**
     * @param commandName
     *     The invalid command name
     *
     * @return
     *     A NoSuchCommandNameException telling the user that a command with that name doesn't exist,
     *     as created by this factory constructor.
     */
    public static NoSuchCommandNameException newInstancefromCommandName(final String commandName) {
        return new NoSuchCommandNameException(String.format("There is no '%s' command.", commandName));
    }
}
