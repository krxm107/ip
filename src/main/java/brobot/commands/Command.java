package brobot.commands;

sealed abstract class Command implements Runnable
    permits ByeCommand, ListCommand, MarkCommand, UnmarkCommand, DeleteCommand, ToDoCommand, DeadlineCommand, EventCommand {

    private final String commandName;
    Command (final String commandName) {
        this.commandName = commandName.strip().toLowerCase();
    }

    @Override
    public abstract void run();
}
