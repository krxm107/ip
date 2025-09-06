package brobot.commands;

import brobot.BroBot;
import brobot.FileIOStatus;
import brobot.Storage;
import brobot.TaskList;

/**
 * This command is created whenever the user wants to mask a task as done.
 */
public final class MarkCommand extends FileIOCommand {

    private final int markIndex;
    private MarkCommand(final int markIndex) {
        super("mark");
        this.markIndex = markIndex;
    }

    /**
     * Factory constructor for MarkCommand.
     */
    public static MarkCommand makeCommand(final String commandName, final String... commandTokens) {
        final int markIndex = Integer.parseInt(commandTokens[0]);
        return new MarkCommand(markIndex);
    }

    @Override
    public FileIOStatus sendBrobotMessage() {
        return FileIOStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().markTask(markIndex);

            assert 1 <= markIndex && markIndex <= TaskList.getSingleton().getSize()
                    : "Command shouldn't succeed on out of range index.";

            final String line1 = "Nice! I've marked this task as done:";
            final String line2 = BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().formatTask(markIndex);

            final String line3 = Storage.SUCCESSFUL_HARD_DRIVE_SAVE;
            return FileIOStatus.makeSuccessStatus(String.join("\n", line1, line2, line3));
        }));
    }
}
