package brobot.commands;

import java.util.List;

import brobot.BroBot;
import brobot.FileIoStatus;
import brobot.TaskList;
import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.tasks.Task;

/**
 * This command runs iff a user wishes to addToTaskList a task to the tasklist.
 */
public final class AddTaskCommand extends FileIoCommand {
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
    public FileIoStatus sendBrobotMessage() {
        try {
            final String line1 = "Got it. I've added this task:";

            TaskList.getSingleton().addToTaskList(makeTask());
            final String line2 = BroBot.FOUR_SPACES_INDENT
                    + TaskList.getSingleton().formatTask(TaskList.getSingleton().getSize());


            final String cheer = FileIoCommand.getSuccessfulFileSaveMessage();

            return FileIoStatus.makeSuccessStatus(String.join(System.lineSeparator(), line1, line2, cheer));
        } catch (final BrobotCommandFormatException badTaskCommand) {
            return badTaskCommand.sendBrobotMessage();
        }
    }
}
