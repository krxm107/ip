package brobot.commands;

import brobot.BroBot;
import brobot.BrobotMessenger;
import brobot.FileIOStatus;
import brobot.Storage;
import brobot.TaskList;

/**
 * This command is used to delete tasks (1-indexed).
 */
public final class DeleteCommand extends FileIOCommand {
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
    public FileIOStatus sendBrobotMessage() {
        final BrobotMessenger orElse = () -> {
            final String line1 = "Noted. I've removed this task:";

            final String line2 = BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().formatTask(deleteIndex);

            TaskList.getSingleton().removeFromTaskList(deleteIndex);

            final String line3 = String.format("Now you have %d tasks in the list.", TaskList.getSingleton().getSize())
                                        + System.lineSeparator();
          
            final String line4 = Storage.SUCCESSFUL_HARD_DRIVE_SAVE;

            return FileIOStatus.makeSuccessStatus(String.join(System.lineSeparator(), line1, line2, line3, line4));
        };

        return FileIOStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(orElse));
    }
}
