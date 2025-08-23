package brobot.tasks;

final class Event extends Task {
    private final String startDate, endDate;
    Event (final String description, final String commandName,
                   final String startDate, final String endDate) {
        super(description, commandName);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private String eventlogMessage = null;
    @Override
    void mark() {
        super.mark();
        this.eventlogMessage = null;
    }

    @Override
    void unmark() {
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
    String toFileReport() {
        return String.format("%s\n%s\n%s\n\n",
                super.toFileReport().substring(0, super.toFileReport().length() - 2),
                this.startDate,
                this.endDate);
    }
}
