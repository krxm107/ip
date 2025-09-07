package brobot.commands;

import brobot.FileIoStatus;
import brobot.brobotexceptions.SomeArgsLeftException;

/**
 * This command runs iff the user wishes to exit BroBot.
 */
public final class ByeCommand extends Command {
    private static ByeCommand byeCommandSingleton = null;
    private ByeCommand() {
        super("bye");

        assert getCommandName().equals("bye") : "Command name should be bye here.";
    }

    /**
     * Lazy factory constructor that generates a singleton instance of ByeCommand.
     * @return
     *     The immutable singleton instance of this command necessary.
     */
    public static ByeCommand getSingleton() {
        if (ByeCommand.byeCommandSingleton == null) {
            ByeCommand.byeCommandSingleton = new ByeCommand();
        }

        return ByeCommand.byeCommandSingleton;
    }

    /**
     * Factory constructor that lazily creates the singleton.
     * @param commandName
     *     The command name
     *
     * @param commandArgs
     * @return
     * @throws SomeArgsLeftException
     */
    public static ByeCommand makeCommand(final String commandName,
        final String... commandArgs) throws SomeArgsLeftException {

        if (commandArgs.length != 0) {
            throw SomeArgsLeftException.fromCommandName(commandName);
        }

        return ByeCommand.getSingleton();
    }

    @Override
    public FileIoStatus sendBrobotMessage() {
        return FileIoStatus.makeSuccessStatus("Bye. Hope to see you again soon!");
    }
}
