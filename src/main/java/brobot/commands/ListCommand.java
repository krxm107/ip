package brobot.commands;

import brobot.brobotexceptions.SomeArgsLeftException;
import brobot.TaskList;

public final class ListCommand extends Command {
    private static ListCommand listCommandSingleton = null;
    private ListCommand () {
        super("list");
    }

    @Override
    public void run() {
        System.out.print(TaskList.getSingleton());
    }

    private static ListCommand getSingleton() {
        if (ListCommand.listCommandSingleton == null) {
            ListCommand.listCommandSingleton = new ListCommand();
        }

        return ListCommand.listCommandSingleton;
    }

    public static ListCommand makeCommand (final String commandName, final String... commandArgs) throws SomeArgsLeftException {
        if (commandArgs.length != 0) {
            throw SomeArgsLeftException.fromCommandName(commandName);
        }

        return ListCommand.getSingleton();
    }
}
