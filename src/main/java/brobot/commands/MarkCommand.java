package brobot.commands;

import brobot.UI;
import brobot.tasks.TaskList;

final class MarkCommand extends Command {

    private final int markIndex;
    private MarkCommand (final int markIndex) {
        super("mark");
        this.markIndex = markIndex;
    }

    static MarkCommand makeCommand (final String commandName, final String... commandTokens) {
        final int markIndex = Integer.parseInt(commandTokens[0]);
        return new MarkCommand(markIndex);
    }

    @Override
    public Runnable getAction() {
        return () -> TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().getTask(markIndex).mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(UI.fourSpacesIndent + TaskList.getSingleton().printFormattedNumberedTask(markIndex));
        });
    }
}
