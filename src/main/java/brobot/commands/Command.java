package brobot.commands;

import brobot.Action;

public sealed abstract class Command implements Action
    permits ByeCommand, ListCommand, MarkCommand, UnmarkCommand, DeleteCommand, AddTaskCommand {

    private final String commandName;
    Command (final String commandName) {
        this.commandName = commandName.strip().toLowerCase();
    }

    public final String getCommandName() {
        return this.commandName;
    }

    @Override
    public abstract Runnable getAction();
}
