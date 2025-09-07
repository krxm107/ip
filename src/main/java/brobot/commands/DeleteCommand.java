package brobot.commands;

import brobot.BroBot;
import brobot.BrobotMessenger;
import brobot.FileIoStatus;
import brobot.TaskList;

/**
 * This command is used to delete tasks (1-indexed).
 */
public final class DeleteCommand extends FileIoCommand {
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
    public FileIoStatus sendBrobotMessage() {
        final BrobotMessenger orElse = () -> {
            final String line1 = "Noted. I've removed this task:";

            final String line2 = BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().formatTask(deleteIndex);

            TaskList.getSingleton().removeFromTaskList(deleteIndex);

            final String cheer = FileIoCommand.getSuccessfulFileSaveMessage();

            return FileIoStatus.makeSuccessStatus(String.join(System.lineSeparator(), line1, line2, cheer));
        };

        return FileIoStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(orElse));
    }
}
