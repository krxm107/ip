package brobot.commands;

import brobot.BroBot;
import brobot.TaskList;

public final class MarkCommand extends Command {

    private final int markIndex;
    private MarkCommand (final int markIndex) {
        super("mark");
        this.markIndex = markIndex;
    }

    public static MarkCommand makeCommand (final String commandName, final String... commandTokens) {
        final int markIndex = Integer.parseInt(commandTokens[0]);
        return new MarkCommand(markIndex);
    }

    @Override
    public void run() {
        TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().markTask(this.markIndex);
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(BroBot.fourSpacesIndent + TaskList.getSingleton().printFormattedNumberedTask(markIndex));
        });
    }
}
