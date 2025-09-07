package brobot.tasks;

import brobot.StringNewlineFormatter;
import brobot.brobotexceptions.BrobotDateFormatException;
import brobot.datesandtimes.BrobotDate;

/**
 * This class represents an event task.
 */
final class EventTask extends Task {
    private final BrobotDate startDate;
    private final BrobotDate endDate;
    private String eventlogMessage = null;

    /**
     * @param description
     *     The task description.
     *
     * @param commandName
     *     The command name that generated this task.
     *
     * @param startDate
     *     The start date of this event.
     *
     * @param endDate
     *     The end date of this event.
     *
     * @throws BrobotDateFormatException
     *     This exception is thrown if the user entered dates that are not in the 'dd MMM yyyy' format.
     */
    EventTask(final String description, final String commandName,
              final String startDate, final String endDate) throws BrobotDateFormatException {
        super(description, commandName);
        this.startDate = BrobotDate.fromString(startDate);
        this.endDate = BrobotDate.fromString(endDate);
    }

    /**
     * Marks the event task as done.
     */
    @Override
    public void mark() {
        super.mark();
        eventlogMessage = null;
    }

    /**
     * Unmarks the event task to show that it is not done yet.
     */
    @Override
    public void unmark() {
        super.unmark();
        eventlogMessage = null;
    }

    /**
     * @return
     *     A user-friendly display of the event task.
     */
    @Override
    public String toString() {
        if (eventlogMessage == null) {
            eventlogMessage = String.format("%s (from: %s to: %s)", super.toString(), startDate, endDate);
        }

        return eventlogMessage;
    }

    /**
     * @return
     *     A serialized version of the task for file IO (as per BroBot domain rules)
     */
    @Override
    public String toFileReport() {
        return String.join(System.lineSeparator(),
                StringNewlineFormatter.removeTrailingNewlines(super.toFileReport(), 2),
                           startDate.toString(),
                           endDate.toString(),
                           "",
                           "");
    }
}
