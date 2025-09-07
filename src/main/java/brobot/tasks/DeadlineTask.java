package brobot.tasks;

import brobot.StringNewlineFormatter;
import brobot.brobotexceptions.BrobotDateFormatException;
import brobot.datesandtimes.BrobotDate;

/**
 * This class represents a deadline task.
 */
final class DeadlineTask extends Task {
    private final BrobotDate deadline;
    private String deadlineLogMessage = null;

    /**
     * @param description
     *     The task description.
     *
     * @param commandName
     *     The name of the command that generated this task.
     *
     * @param deadline
     *     The deadline of the deadlined task.
     *
     * @throws BrobotDateFormatException
     *     Throws a BrobotDateFormatException if the user enters a deadline that is not in the 'dd MMM yyyy' format.
     */
    DeadlineTask(final String description,
                 final String commandName,
                 final String deadline) throws BrobotDateFormatException {

        super(description, commandName);
        this.deadline = BrobotDate.fromString(deadline);
    }

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
     *     Returns a user-friendly display of the deadlined task.
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
     *     Returns a serialized version of the task for file IO (as per BroBot domain rules)
     */
    @Override
    public String toFileReport() {
        return String.join(System.lineSeparator(),
                StringNewlineFormatter.removeTrailingNewlines(super.toFileReport(), 2),
                            deadline.toString(),
                            "",
                            "");
    }
}
