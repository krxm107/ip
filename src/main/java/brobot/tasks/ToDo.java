package brobot.tasks;

final class ToDo extends Task {
    ToDo (final String description, final String commandName) {
        super(description, commandName);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    String toFileReport() {
        return super.toFileReport();
    }
}