package brobot.tasks;

import brobot.brobotexceptions.BrobotDateFormatException;
import brobot.datesandtimes.BrobotDate;

final class Deadline extends Task {
    private final BrobotDate deadline;
    Deadline(final String description, final String commandName, final String deadline) throws BrobotDateFormatException {
        super(description, commandName);
        this.deadline = BrobotDate.fromString(deadline);
    }

    private String deadlineLogMessage = null;

    @Override
    public void mark() {
        super.mark();
        deadlineLogMessage = null;
    }

    @Override
    public void unmark() {
        super.unmark();
        deadlineLogMessage = null;
    }

    @Override
    public String toString() {
        if (deadlineLogMessage == null) {
            deadlineLogMessage = String.format("%s (by: %s)", super.toString(), deadline);
        }

        return deadlineLogMessage;
    }

    @Override
    public String toFileReport() {
        return String.format("%s\n%s\n\n", super.toFileReport().substring(0, super.toFileReport().length() - 2),
                deadline);
    }
}
