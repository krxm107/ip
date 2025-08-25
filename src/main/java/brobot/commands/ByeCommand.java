package brobot.commands;

final class ByeCommand extends Command {
    private static ByeCommand byeCommandSingleton = null;
    private ByeCommand() {
        super("Bye");
    }

    public static final ByeCommand getSingleton() {
        if (ByeCommand.byeCommandSingleton == null) {
            ByeCommand.byeCommandSingleton = new ByeCommand();
        }

        return ByeCommand.byeCommandSingleton;
    }

    @Override
    public void run() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
