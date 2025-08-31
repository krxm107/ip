package brobot.commands;


import brobot.BroBot;
import brobot.TaskList;

/**
 * This command is created whenever the user wants to unmark a task to reflect that it is not done yet.
 */
public final class UnmarkCommand extends Command {

    private final int unmarkIndex;
    private UnmarkCommand(final int unmarkIndex) {
        super("unmark");
        this.unmarkIndex = unmarkIndex;
    }

    /**
     * Factory constructor for UnmarkCommand.
     */
    public static UnmarkCommand makeCommand(final String commandName, final String... commandArgs) {
        final int markIndex = Integer.parseInt(commandArgs[0]);
        return new UnmarkCommand(markIndex);
    }

    @Override
    public void performBrobotAction() {
        TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().unmarkTask(unmarkIndex);
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().printFormattedNumberedTask(unmarkIndex));
        });
    }
}
