package brobot.commands;

import brobot.BrobotAction;

/**
 * This class is the Abstract Base Class for BroBot Commands.
 *
 * For safety reasons, all commands are immutable by design.
 */
public abstract class Command implements BrobotAction {

    private final String commandName;
    Command(final String commandName) {
        this.commandName = commandName.strip().toLowerCase();
    }

    /**
     * @return
     *     The name of the command.
     */
    public final String getCommandName() {
        return commandName;
    }

    /**
     * Runs the command.
     */
    @Override
    public abstract void performBrobotAction();
}
