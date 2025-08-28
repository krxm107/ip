package brobot.commands;

public abstract class Command implements Runnable {

    private final String commandName;
    Command(final String commandName) {
        this.commandName = commandName.strip().toLowerCase();
    }

    public final String getCommandName() {
        return commandName;
    }

    @Override
    public abstract void run();
}
