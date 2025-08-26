package brobot.commands;

import brobot.brobotexceptions.SomeArgsLeftException;

final class ByeCommand extends Command {
    private static ByeCommand byeCommandSingleton = null;
    private ByeCommand() {
        super("bye");
    }

    private static ByeCommand getSingleton() {
        if (ByeCommand.byeCommandSingleton == null) {
            ByeCommand.byeCommandSingleton = new ByeCommand();
        }

        return ByeCommand.byeCommandSingleton;
    }

    static ByeCommand makeCommand (final String commandName, final String... commandArgs) throws SomeArgsLeftException {
        if (commandArgs.length != 0) {
            throw SomeArgsLeftException.fromCommandName(commandName);
        }

        return ByeCommand.getSingleton();
    }

    @Override
    public Runnable getAction() {
        return () -> System.out.println("Bye. Hope to see you again soon!");
    }
}
