package brobot.commands;

import brobot.BroBot;
import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.tasks.Task;
import brobot.TaskList;

import java.util.List;

public final class AddTaskCommand extends Command {
    private final List<String> commandTokens;

    private AddTaskCommand(final String commandName, final String... commandTokens) {
        super(commandName);
        this.commandTokens = List.of(commandTokens);
    }

    public static AddTaskCommand makeCommand(final String commandName, final String... commandTokens) {
        return new AddTaskCommand(commandName, commandTokens);
    }

    private Task taskToAdd = null;

    private Task makeTask() throws BrobotCommandFormatException {
        if (taskToAdd == null) {
            taskToAdd = Task.createTask(getCommandName(), commandTokens.toArray(String[]::new));
        }

        return taskToAdd;
    }

    @Override
    public void run() {
        try {
            System.out.println("Got it. I've added this task:");

            TaskList.getSingleton().add(makeTask());
            System.out.println(BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().printFormattedNumberedTask(TaskList.getSingleton().size()));


            System.out.printf("Now you have %d tasks in the list.\n", TaskList.getSingleton().size());
        } catch (final BrobotCommandFormatException badTaskCommand) {
            BroBot.sendPrintMessage(badTaskCommand);
        }
    }
}
