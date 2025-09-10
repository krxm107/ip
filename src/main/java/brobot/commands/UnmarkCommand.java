package brobot.commands;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import brobot.BroBot;
import brobot.FileIoStatus;
import brobot.TaskList;

/**
 * This command is created whenever the user wants to unmark tasks to reflect that they are not done yet.
 */
public final class UnmarkCommand extends FileIoCommand implements MassOpCommand {

    private final IntStream unmarkIndices;
    private UnmarkCommand(final IntStream unmarkIndices) {
        super("unmark");
        this.unmarkIndices = unmarkIndices;
    }

    /**
     *     Factory constructor for UnmarkCommand.
     *     @param commandName
     *     The name of the command that generated this new instance.
     *     @param commandArgs
     *     The arguments passed to the command.
     *     @return
     *     The new instance of UnmarkCommand.
     */
    public static UnmarkCommand makeCommand(final String commandName, final String... commandArgs) {
        if (commandArgs.length == 0) {
            return new UnmarkCommand(IntStream.empty());
        } else if (commandArgs.length == 1) {
            if (commandArgs[0].equalsIgnoreCase("all")) {
                final IntStream unmarkIndices = IntStream.iterate(TaskList.getSingleton().getSize(),
                                                                    (final int idx) -> idx >= 1,
                                                                    (final int idx) -> idx - 1);
                return new UnmarkCommand(unmarkIndices);
            } else {
                final int unmarkIndex = Integer.parseInt(commandArgs[0]);
                return new UnmarkCommand(IntStream.of(unmarkIndex));
            }
        } else {
            final IntStream unmarkIndices = Stream.of(commandArgs).mapToInt(Integer::parseInt);
            return new UnmarkCommand(unmarkIndices);
        }
    }

    @Override
    public FileIoStatus sendBrobotMessage() {
        return FileIoStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(() -> {
            final StringBuilder ans = new StringBuilder("OK, I've marked these tasks as not done yet:");
            ans.append(System.lineSeparator());

            unmarkIndices.boxed().sorted(Comparator.reverseOrder()).forEach((final Integer unmarkIndex) -> {
                TaskList.getSingleton().unmarkTask(unmarkIndex);
                ans.append(BroBot.FOUR_SPACES_INDENT).append(TaskList.getSingleton().formatTask(unmarkIndex));
                ans.append(System.lineSeparator());
            });

            ans.append(FileIoCommand.getSuccessfulFileSaveMessage());

            return FileIoStatus.makeSuccessStatus(ans.toString());
        }));
    }
}
