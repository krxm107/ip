package brobot.commands;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import brobot.BroBot;
import brobot.FileIoStatus;
import brobot.TaskList;

/**
 * This command is created whenever the user wants to mark tasks as done.
 */
public final class MarkCommand extends FileIoCommand implements MassOpCommand {

    private final IntStream markIndices;
    private MarkCommand(final IntStream markIndices) {
        super("mark");
        this.markIndices = markIndices;
    }

    /**
     * Factory constructor for MarkCommand.
     */
    public static MarkCommand makeCommand(final String commandName, final String... commandTokens) {
        if (commandTokens.length == 0) {
            return new MarkCommand(IntStream.empty());
        } else if (commandTokens.length == 1) {
            if (commandTokens[0].equalsIgnoreCase("all")) {
                final IntStream markIndices = IntStream.iterate(TaskList.getSingleton().getSize(),
                        (final int idx) -> idx >= 1,
                        (final int idx) -> idx - 1);
                return new MarkCommand(markIndices);
            } else {
                final int markIndex = Integer.parseInt(commandTokens[0]);
                return new MarkCommand(IntStream.of(markIndex));
            }
        } else {
            final IntStream markIndices = Stream.of(commandTokens).mapToInt(Integer::parseInt);
            return new MarkCommand(markIndices);
        }
    }

    @Override
    public FileIoStatus sendBrobotMessage() {
        return FileIoStatus.makeSuccessStatus(TaskList.getSingleton().noTaskCheerOrElse(() -> {
            final StringBuilder ans = new StringBuilder("Nice! I've marked these tasks as done:");
            ans.append(System.lineSeparator());

            markIndices.boxed().sorted(Comparator.reverseOrder()).forEach((final Integer markIndex) -> {
                TaskList.getSingleton().markTask(markIndex);
                ans.append(BroBot.FOUR_SPACES_INDENT).append(TaskList.getSingleton().formatTask(markIndex));
                ans.append(System.lineSeparator());
            });

            ans.append(FileIoCommand.getSuccessfulFileSaveMessage());

            return FileIoStatus.makeSuccessStatus(ans.toString());
        }));
    }
}
