package brobot.tasks;

import brobot.brobotexceptions.BrobotDateFormatException;
import brobot.datesandtimes.BrobotDate;

final class Event extends Task {
    private final BrobotDate startDate, endDate;
    Event (final String description, final String commandName,
                   final String startDate, final String endDate) throws BrobotDateFormatException {
        super(description, commandName);
        this.startDate = BrobotDate.fromString(startDate);
        this.endDate = BrobotDate.fromString(endDate);
    }

    private String eventlogMessage = null;
    @Override
    public void mark() {
        super.mark();
        this.eventlogMessage = null;
    }

    @Override
    public void unmark() {
        super.unmark();
        this.eventlogMessage = null;
    }

    @Override
    public String toString() {
        if (this.eventlogMessage == null) {
            this.eventlogMessage = String.format("%s (from: %s to: %s)", super.toString(), this.startDate, this.endDate);
        }

        return this.eventlogMessage;
    }

    @Override
    public String toFileReport() {
        return String.format("%s\n%s\n%s\n\n",
                super.toFileReport().substring(0, super.toFileReport().length() - 2),
                this.startDate,
                this.endDate);
    }
}
