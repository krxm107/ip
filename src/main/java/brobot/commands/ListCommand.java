package brobot.commands;

import brobot.TaskList;
import brobot.brobotexceptions.SomeArgsLeftException;

/**
 * This command is created for listing the tasks in the tasklist to the user.
 */
public final class ListCommand extends Command {
    private static ListCommand listCommandSingleton = null;
    private ListCommand() {
        super("list");
    }

    private static ListCommand getSingleton() {
        if (ListCommand.listCommandSingleton == null) {
            ListCommand.listCommandSingleton = new ListCommand();
        }

        return ListCommand.listCommandSingleton;
    }

    /**
     * @param commandName
     *     The command name that generated the new instance.
     *
     * @param commandArgs
     *     The command arguments.
     *
     * @return
     *     A new instance of ListCommand.
     *
     * @throws SomeArgsLeftException
     *     This exception is thrown iff there is a problem parsing the commandArgs.
     */
    public static ListCommand makeCommand(final String commandName,
        final String... commandArgs) throws SomeArgsLeftException {

        if (commandArgs.length != 0) {
            throw SomeArgsLeftException.fromCommandName(commandName);
        }

        return ListCommand.getSingleton();
    }

    @Override
    public void performBrobotAction() {
        System.out.print(TaskList.getSingleton());
    }
}
