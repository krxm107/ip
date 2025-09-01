package brobot.commands;

import brobot.BroBot;
import brobot.TaskList;

/**
 * This command is created whenever the user wants to mask a task as done.
 */
public final class MarkCommand extends Command {

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
    public String sendMessage() {
        return TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().markTask(markIndex);
            final String line1 = "Nice! I've marked this task as done:";
            final String line2 = BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().printFormattedNumberedTask(markIndex);

            return String.join("\n", line1, line2);
        });
    }
}
