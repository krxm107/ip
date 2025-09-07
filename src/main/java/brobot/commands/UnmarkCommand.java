package brobot.commands;


import brobot.BroBot;
import brobot.FileIoStatus;
import brobot.Storage;
import brobot.TaskList;

/**
 * This command is created whenever the user wants to unmark a task to reflect that it is not done yet.
 */
public final class UnmarkCommand extends FileIoCommand {

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
    public FileIoStatus sendBrobotMessage() {
        return FileIoStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().unmarkTask(unmarkIndex);
            final String line1 = "OK, I've marked this task as not done yet:";
            final String line2 = BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().formatTask(unmarkIndex);

            final String cheer = FileIoCommand.getSuccessfulFileSaveMessage();

            return FileIoStatus.makeSuccessStatus(String.join(System.lineSeparator(), line1, line2, cheer));
        }));
    }
}
