package brobot.commands;

import java.util.List;

import brobot.BroBot;
import brobot.TaskList;
import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.tasks.Task;

/**
 * This command runs iff a user wishes to add a task to the tasklist.
 */
public final class AddTaskCommand extends Command {
    private final List<String> commandTokens;
    private Task taskToAdd = null;

    private AddTaskCommand(final String commandName, final String... commandTokens) {
        super(commandName);
        this.commandTokens = List.of(commandTokens);
    }

    /**
     * @param commandName
     *     The name of the command that generated the new instance.
     *
     * @param commandTokens
     *     The command arguments (excluding name) that generated this instance.
     *
     * @return
     *     A new instance of AddTaskCommand as created by this factory constructor.
     */
    public static AddTaskCommand makeCommand(final String commandName, final String... commandTokens) {
        return new AddTaskCommand(commandName, commandTokens);
    }

    private Task makeTask() throws BrobotCommandFormatException {
        if (taskToAdd == null) {
            taskToAdd = Task.createTask(getCommandName(), commandTokens.toArray(String[]::new));
        }

        return taskToAdd;
    }

    /**
     * Runs the command
     */
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
