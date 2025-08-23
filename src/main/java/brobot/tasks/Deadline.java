package brobot.tasks;

final class Deadline extends Task {
    private final String deadline;
    Deadline (final String description, final String commandName, final String deadline) {
        super(description, commandName);
        this.deadline = deadline;
    }

    private String deadlineLogMessage = null;

    @Override
    void mark() {
        super.mark();
        this.deadlineLogMessage = null;
    }

    @Override
    void unmark() {
        super.unmark();
        this.deadlineLogMessage = null;
    }

    @Override
    public String toString() {
        if (this.deadlineLogMessage == null) {
            this.deadlineLogMessage = String.format("%s (by: %s)", super.toString(), this.deadline);
        }

        return this.deadlineLogMessage;
    }

    @Override
    String toFileReport() {
        return String.format("%s\n%s\n\n", super.toFileReport().substring(0, super.toFileReport().length() - 2),
                this.deadline);
    }
}
