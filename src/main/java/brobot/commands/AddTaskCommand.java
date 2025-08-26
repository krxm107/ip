package brobot.commands;

import brobot.UI;
import brobot.tasks.TaskList;

import java.util.List;

public final class AddTaskCommand extends Command {
    private final List<String> commandTokens;
    private AddTaskCommand (final String commandName, final String... commandTokens) {
        super(commandName);
        this.commandTokens = List.of(commandTokens);
    }

    static AddTaskCommand makeCommand (final String commandName, final String... commandTokens) {
        return new AddTaskCommand(commandName, commandTokens);
    }

    @Override
    public Runnable getAction() {
        return () -> {
            System.out.println("Got it. I've added this task:");
            System.out.println(UI.fourSpacesIndent + TaskList.getSingleton().printFormattedNumberedTask(TaskList.getSingleton().size()));
            System.out.printf("Now you have %d tasks in the list.\n", TaskList.getSingleton().size());
        };
    }
}
