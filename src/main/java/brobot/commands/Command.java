package brobot.commands;

import brobot.Action;

sealed abstract class Command implements Action
    permits ByeCommand, ListCommand, MarkCommand, UnmarkCommand, DeleteCommand, AddTaskCommand {

    private final String commandName;
    Command (final String commandName) {
        this.commandName = commandName.strip().toLowerCase();
    }

    @Override
    public abstract Runnable getAction();
}
