package brobot.commands;

import brobot.TaskList;
import brobot.tasks.Task;

/**
 * This command is used to find tasks by keyword (literal String)
 */
public final class FindCommand extends Command {

    private final String keyword;
    private FindCommand(final String keyword) {
        super("find");
        this.keyword = keyword;
    }

    /**
     * Constructs a new instance of FindCommand.
     */
    public static FindCommand makeCommand(final String... commandArgs) {
        return new FindCommand(String.join(" ", commandArgs));
    }

    @Override
    public void run() {
        for (int i = 1; i <= TaskList.getSingleton().size(); i++) {
            final Task currTask = TaskList.getSingleton().getTask(i);
            if (currTask.findKeywordInTaskDescription(keyword)) {
                System.out.println(TaskList.printFormattedNumberedTask(i, currTask));
            }
        }
    }
}
