package brobot.commands;

import brobot.tasks.TaskList;

final class ListCommand extends Command {
    private static ListCommand listCommandSingleton = null;
    private ListCommand () {
        super("list");
    }

    @Override
    public void run() {
        System.out.println(TaskList.getSingleton());
    }

    public static ListCommand getSingleton() {
        if (ListCommand.listCommandSingleton == null) {
            ListCommand.listCommandSingleton = new ListCommand();
        }

        return ListCommand.listCommandSingleton;
    }
}
