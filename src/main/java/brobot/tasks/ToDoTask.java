package brobot.tasks;

/**
 * This class represents a ToDoTask task.
 */
final class ToDoTask extends Task {
    /**
     * @param description
     *     The description of the ToDoTask.
     *
     * @param commandName
     *     The command name that generated this task.
     */
    ToDoTask(final String description, final String commandName) {
        super(description, commandName);
    }
}
