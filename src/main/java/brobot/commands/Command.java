package brobot.commands;

import brobot.BrobotMessenger;
import brobot.FileIoStatus;

/**
 * This class is the Abstract Base Class for BroBot Commands.
 *
 * For safety reasons, all commands are immutable by design.
 */
public abstract class Command implements BrobotMessenger {

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
    public abstract FileIoStatus sendBrobotMessage();
}
