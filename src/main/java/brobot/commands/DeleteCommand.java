package brobot.commands;

import brobot.BroBot;
import brobot.TaskList;

/**
 * This command is used to delete tasks (1-indexed).
 */
public final class DeleteCommand extends Command {
    private final int deleteIndex;
    private DeleteCommand(final int deleteIndex) {
        super("delete");
        this.deleteIndex = deleteIndex;
    }

    /**
     * Factory constructor for DeleteCommand.
     *
     * @param commandName
     *     The name of the command that generated this DeleteCommand.
     *
     * @param commandArgs
     *     The command arguments
     *
     * @return
     *     A new instance of DeleteCommand.
     */
    public static DeleteCommand makeCommand(final String commandName, final String... commandArgs) {
        final int markIndex = Integer.parseInt(commandArgs[0]);
        return new DeleteCommand(markIndex);
    }

    @Override
    public void run() {
        final Runnable orElse = () -> {
            System.out.println("Noted. I've removed this task:");

            System.out.println(BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().printFormattedNumberedTask(deleteIndex));

            TaskList.getSingleton().remove(deleteIndex);
            System.out.printf("Now you have %d tasks in the list.\n", TaskList.getSingleton().size());
        };

        TaskList.getSingleton().noTaskCheerOrElse(orElse);
    }
}
