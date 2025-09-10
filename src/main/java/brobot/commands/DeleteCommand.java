package brobot.commands;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import brobot.BroBot;
import brobot.BrobotMessenger;
import brobot.FileIoStatus;
import brobot.TaskList;
import brobot.tasks.Task;

/**
 * This command is used to delete tasks (1-indexed).
 */
public final class DeleteCommand extends FileIoCommand implements MassOpCommand {
    private final IntStream deleteIndices;
    private DeleteCommand(final IntStream deleteIndices) {
        super("delete");
        this.deleteIndices = deleteIndices;
    }

    /**
     * Factory constructor for DeleteCommand.
     *
     * @param commandName
     *     The name of the command that generated this DeleteCommand.
     *
     * @param commandArgs
     *     The command arguments
     *
     * @return
     *     A new instance of DeleteCommand.
     */
    public static DeleteCommand makeCommand(final String commandName, final String... commandArgs) {
        if (commandArgs.length == 0) {
            return new DeleteCommand(IntStream.empty());
        } else if (commandArgs.length == 1) {
            if (commandArgs[0].equalsIgnoreCase("all")) {
                final IntStream deleteIndices = IntStream.iterate(TaskList.getSingleton().getSize(),
                        (final int idx) -> idx >= 1,
                        (final int idx) -> idx - 1);
                return new DeleteCommand(deleteIndices);
            } else {
                final int deleteIndex = Integer.parseInt(commandArgs[0]);
                return new DeleteCommand(IntStream.of(deleteIndex));
            }
        } else {
            final IntStream deleteIndices = Stream.of(commandArgs).mapToInt(Integer::parseInt);
            return new DeleteCommand(deleteIndices);
        }
    }

    @Override
    public FileIoStatus sendBrobotMessage() {
        final BrobotMessenger orElse = () -> {
            final StringBuilder ans = new StringBuilder("Noted. I've removed these tasks:");
            ans.append(System.lineSeparator());

            deleteIndices.boxed().sorted(Comparator.reverseOrder()).forEach((final Integer deleteIndex) -> {
                final String formattedTask = TaskList.getSingleton().formatTask(deleteIndex);
                TaskList.getSingleton().removeFromTaskList(deleteIndex);

                ans.append(formattedTask);
                ans.append(System.lineSeparator());
            });

            ans.append(FileIoCommand.getSuccessfulFileSaveMessage());
            return FileIoStatus.makeSuccessStatus(ans.toString());
        };

        return FileIoStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(orElse));
    }
}
