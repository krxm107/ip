package brobot.commands;


import brobot.BroBot;
import brobot.FileIOStatus;
import brobot.TaskList;

/**
 * This command is created whenever the user wants to unmark a task to reflect that it is not done yet.
 */
public final class UnmarkCommand extends FileIOCommand {

    private final int unmarkIndex;
    private UnmarkCommand(final int unmarkIndex) {
        super("unmark");
        this.unmarkIndex = unmarkIndex;
    }

    /**
     *     Factory constructor for UnmarkCommand.
     *     @param commandName
     *     The name of the command that generated this new instance.
     *     @param commandArgs
     *     The arguments passed to the command.
     *     @return
     *     The new instance of UnmarkCommand.
     */
    public static UnmarkCommand makeCommand(final String commandName, final String... commandArgs) {
        final int markIndex = Integer.parseInt(commandArgs[0]);
        return new UnmarkCommand(markIndex);
    }

    @Override
    public FileIOStatus sendBrobotMessage() {
        return FileIOStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().unmarkTask(unmarkIndex);
            final String line1 = "OK, I've marked this task as not done yet:";
            final String line2 = BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().formatTask(unmarkIndex);

            final String line3 = "Your tasks have successfully been saved to the hard drive.";

            return FileIOStatus.makeSuccessStatus(String.join("\n", line1, line2, line3));
        }));
    }
}
