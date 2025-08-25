package brobot.brobotexceptions;

import brobot.tasks.TaskList;

public final class TaskIndexOutOfBoundsException extends BrobotCommandFormatException {
    private TaskIndexOutOfBoundsException (final String mainMessage) {
        super(mainMessage);
    }

    public static TaskIndexOutOfBoundsException fromBadIndex (final int badIndex) {
        final String line1 = String.format("There are only %d tasks.", TaskList.getSingleton().size());
        final String line2 = String.format("Please enter a number from 1 to %d.", TaskList.getSingleton().size());

        return new TaskIndexOutOfBoundsException(String.join("\n", line1, line2));
    }
}
