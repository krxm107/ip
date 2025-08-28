package brobot.tasks;

import java.util.function.BiFunction;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.brobotexceptions.BrobotDateFormatException;

public abstract class Task {
    private static int nextFreeID = 1;

    private final String baseObjective;
    private final int id;
    private final String commandName;

    Task(final String baseObjective, final String commandName) {
        id = Task.nextFreeID;
        this.baseObjective = baseObjective;
        this.commandName = commandName;
        Task.nextFreeID++;
    }

    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }

    @Override
    public final int hashCode() {
        return id;
    }

    private String baseLogMessage = null;

    private boolean isDone = false;

    public void mark() {
        isDone = true;
        baseLogMessage = null;
    }

    public void unmark() {
        isDone = false;
        baseLogMessage = null;
    }

    @Override
    public String toString() {
        if (baseLogMessage == null) {
            baseLogMessage = String.format("[%c][%c] %s",
                    Character.toUpperCase(commandName.charAt(0)),
                    ((isDone) ? 'X' : ' '),
                    baseObjective);
        }

        return baseLogMessage;
    }

    public final boolean findKeywordInTaskDescription(final String keyword) {
        final String taskDescription = baseObjective;
        return taskDescription.contains(keyword);
    }

    public String toFileReport() {
        return String.format("%s\n%s\n%s\n\n",
                commandName,
                isDone,
                baseObjective);

    }

    public static final Task fromFileReport(final String fileReport) {
        final String[] fileReportLines = fileReport.split("\n");

        try {
            switch (fileReportLines.length) {

            // Fallthrough
            case 3: {
                final Task ans = new ToDo(fileReportLines[2], fileReportLines[0]);
                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            // Fallthrough
            case 4: {
                final Task ans = new Deadline(fileReportLines[2], fileReportLines[0], fileReportLines[3]);
                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            // Fallthrough
            case 5: {
                final Task ans = new Event(fileReportLines[2],
                        fileReportLines[0],
                        fileReportLines[3],
                        fileReportLines[4]);

                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            // Fallthrough
            default: {
                return null;
            }

            }
        } catch (final BrobotDateFormatException brobotDateFormatException) {
            return null;
        }
    }

    public static final Task createTask(final String commandName, final String... commandTokens) throws BrobotCommandFormatException {
        final String[] ans = new String[commandTokens.length + 1];
        ans[0] = commandName;
        for (int i = 0; i < commandTokens.length; i++) {
            ans[i + 1] = commandTokens[i];
        }

        return Task.createTask(ans);
    }

    private static final Task createTask(final String... commandTokens) throws BrobotDateFormatException {
        final BiFunction<Integer, Integer, String> stringJoiner = (final Integer startIdx, final Integer endIdx) -> {
            final String[] slice = new String[endIdx - startIdx];
            for (int i = startIdx; i < endIdx; i++) {
                slice[i - startIdx] = commandTokens[i];
            }

            return String.join(" ", slice);
        };

        if (commandTokens[0].equalsIgnoreCase("todo")) {
            return new ToDo(stringJoiner.apply(1, commandTokens.length), "ToDo");
        }

        if (commandTokens[0].equalsIgnoreCase("deadline")) {
            int firstByIndex = -1;
            for (int i = 1; i < commandTokens.length; i++) {
                if (commandTokens[i].equalsIgnoreCase("by")) {
                    firstByIndex = i;
                    break;
                }
            }

            final String description = stringJoiner.apply(1, firstByIndex);
            final String deadline = stringJoiner.apply(firstByIndex + 1, commandTokens.length);

            return new Deadline(description, "Deadline", deadline);
        }

        int firstFromIndex = -1;
        for (int i = 1; i < commandTokens.length; i++) {
            if (commandTokens[i].equalsIgnoreCase("from")) {
                firstFromIndex = i;
                break;
            }
        }

        int firstToIndex = firstFromIndex - 1;
        for (int i = firstFromIndex + 1; i < commandTokens.length; i++) {
            if (commandTokens[i].equalsIgnoreCase("to")) {
                firstToIndex = i;
                break;
            }
        }

        final String description = stringJoiner.apply(1, firstFromIndex);
        final String fromDate = stringJoiner.apply(firstFromIndex + 1, firstToIndex);
        final String toDate = stringJoiner.apply(firstToIndex + 1, commandTokens.length);

        return new Event(description, "Event", fromDate, toDate);
    }
}