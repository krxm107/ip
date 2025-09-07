package brobot.commands;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import brobot.FileIOStatus;
import brobot.TaskList;

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
    public FileIOStatus sendBrobotMessage() {
        return FileIOStatus.makeSuccessStatus(
                IntStream.rangeClosed(1, TaskList.getSingleton().getSize())
                         .filter((final int i) -> TaskList.getSingleton()
                                                          .getTask(i)
                                                          .findKeywordInTaskDescriptionIgnoreCase(keyword))
                         .<String>mapToObj((final int i) -> TaskList.formatTask(i, TaskList.getSingleton().getTask(i)))
                         .collect(Collectors.joining(System.lineSeparator()))
        );
    }
}
