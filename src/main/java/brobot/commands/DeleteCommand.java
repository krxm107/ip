package brobot.commands;

import brobot.BroBot;
import brobot.tasks.TaskList;

final class DeleteCommand extends Command {
    private final int deleteIndex;
    private DeleteCommand (final int deleteIndex) {
        super("delete");
        this.deleteIndex = deleteIndex;
    }

    static DeleteCommand makeCommand (final String commandName, final String... commandArgs) {
        final int markIndex = Integer.parseInt(commandArgs[0]);
        return new DeleteCommand(markIndex);
    }

    @Override
    public void run() {
        final Runnable orElse = () -> {
            System.out.println("Noted. I've removed this task:");
            System.out.println(BroBot.fourSpacesIndent + TaskList.getSingleton().printFormattedNumberedTask(deleteIndex));
            TaskList.getSingleton().remove(deleteIndex);
            System.out.printf("Now you have %d tasks in the list.\n", TaskList.getSingleton().size());
        };

        TaskList.getSingleton().noTaskCheerOrElse(orElse);
    }
}
