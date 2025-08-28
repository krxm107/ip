package brobot.tasks;

import brobot.brobotexceptions.BrobotDateFormatException;
import brobot.datesandtimes.BrobotDate;

/**
 * This class represents a deadline task.
 */
final class DeadlineTask extends Task {
    private final BrobotDate deadline;

    /**
     * @param description
     * The task description.
     *
     * @param commandName
     * The name of the command that generated this task.
     *
     * @param deadline
     * The deadline of the deadlined task.
     *
     * @throws BrobotDateFormatException
     * A BrobotDateFormatException is thrown if the user enters a deadline that is not in the 'dd MMM yyyy' format.
     */
    DeadlineTask(final String description, final String commandName, final String deadline) throws BrobotDateFormatException {
        super(description, commandName);
        this.deadline = BrobotDate.fromString(deadline);
    }

    private String deadlineLogMessage = null;

    /**
     * Marks the deadlined task as done.
     */
    @Override
    public void mark() {
        super.mark();
        deadlineLogMessage = null;
    }

    /**
     * Unmarks the deadlined task to show that it is not done yet.
     */
    @Override
    public void unmark() {
        super.unmark();
        deadlineLogMessage = null;
    }

    /**
     * @return
     * A user-friendly display of the deadlined task.
     */
    @Override
    public String toString() {
        if (deadlineLogMessage == null) {
            deadlineLogMessage = String.format("%s (by: %s)", super.toString(), deadline);
        }

        return deadlineLogMessage;
    }

    /**
     * @return
     * A serialized version of the task for file IO.
     */
    @Override
    public String toFileReport() {
        return String.format("%s\n%s\n\n", super.toFileReport().substring(0, super.toFileReport().length() - 2),
                deadline);
    }
}
