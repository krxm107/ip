package brobot.commands;


import brobot.UI;
import brobot.tasks.TaskList;

final class UnmarkCommand extends Command {

    private final int unmarkIndex;
    private UnmarkCommand (final int unmarkIndex) {
        super("unmark");
        this.unmarkIndex = unmarkIndex;
    }

    static UnmarkCommand makeCommand (final String commandName, final String... commandArgs) {
        final int markIndex = Integer.parseInt(commandArgs[0]);
        return new UnmarkCommand(markIndex);
    }

    @Override
    public Runnable getAction() {
        return () -> TaskList.getSingleton().noTaskCheerOrElse(() -> {
            TaskList.getSingleton().getTask(this.unmarkIndex).unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(UI.fourSpacesIndent + TaskList.getSingleton().printFormattedNumberedTask(this.unmarkIndex));
        });
    }
}
