package brobot.tasks;

import java.util.function.BiFunction;

import brobot.BrobotFileIoSerializable;
import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.brobotexceptions.BrobotDateFormatException;

/**
 * Abstract Base Class for the Tasks the user creates.
 *
 * Tasks can be serialized and deserialized for File IO (custom rules)
 * because the interface BrobotFileIoSerializable is implemented.
 */
public abstract class Task implements BrobotFileIoSerializable {
    private static int nextFreeID = 1;

    private final String baseObjective;
    private final int id;
    private final String commandName;


    private String baseLogMessage = null;

    private boolean isDone = false;

    /**
     * @param baseObjective
     *     The description of the task.
     *
     * @param commandName
     *     The name of the command that generated the task.
     */
    Task(final String baseObjective, final String commandName) {
        id = Task.nextFreeID;
        this.baseObjective = baseObjective;
        this.commandName = commandName;
        Task.nextFreeID++;
    }

    /**
     * @param o
     *     The reference object with which to compare.
     *
     * @return
     *     Since no two tasks are the same, referential equality suffices here.
     *     This will run in O(1) time and require O(1) extra space per call to equals here.
     *
     *     Checks whether the invocation target is the same object in memory as the other object.
     */
    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }

    /**
     * @return
     *     A hashCode for this task based on its ID.
     */
    @Override
    public final int hashCode() {
        return id;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        isDone = true;
        baseLogMessage = null;
    }

    /**
     * Unmarks this task to show that it is not done yet.
     */
    public void unmark() {
        isDone = false;
        baseLogMessage = null;
    }

    /**
     * @return
     *     Returns a user-friendly display of the task.
     */
    @Override
    public String toString() {
        if (baseLogMessage == null) {
            baseLogMessage = String.format("[%c][%c] %s",
                    Character.toUpperCase(commandName.charAt(0)), ((isDone) ? 'X' : ' '),
                    baseObjective);
        }

        return baseLogMessage;
    }

    private String getBaseObjective() {
        return baseObjective;
    }

    private String getTaskDescription() {
        return getBaseObjective();
    }

    /**
     * @param keyword
     *     The keyword to search for in the task (literal string).
     *
     * @return
     *     A boolean indicating whether the task description contains the keyword passed in (literal strings).
     */
    public final boolean findKeywordInTaskDescriptionIgnoreCase(final String keyword) {
        final String taskDescription = getTaskDescription();
        return taskDescription.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * @return
     *     A serialized version of the task for file IO (according to BroBot domain rules).
     */
    public String toFileReport() {
        return String.join(System.lineSeparator(),
                           commandName,
                            isDone + "",
                            baseObjective,
                           "",
                           "");
    }

    /**
     * @param fileReport
     *     The file report in task-serialized format (according to BroBot domain rules).
     *
     * @return
     *     A Task instance deserialized from the file report by this factory constructor.
     */
    public static final Task fromFileReport(final String fileReport) {
        final String[] fileReportLines = fileReport.split(System.lineSeparator());

        try {
            switch (fileReportLines.length) {

            // Fallthrough
            case 3: {
                final Task ans = new ToDoTask(fileReportLines[2], fileReportLines[0]);
                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            // Fallthrough
            case 4: {
                final Task ans = new DeadlineTask(fileReportLines[2], fileReportLines[0], fileReportLines[3]);
                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            // Fallthrough
            case 5: {
                final Task ans = new EventTask(fileReportLines[2],
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

    /**
     * @param commandName
     *     The name of the command to generate the new task.
     *
     * @param commandTokens
     *     The arguments of the command that generates the new task.
     *
     * @return
     *     The task as generated from the arguments of this function, as per this factory constructor.
     *
     * @throws BrobotCommandFormatException
     *     A BrobotCommandFormatException is thrown iff there is a problem processing the user command.
     */
    public static final Task createTask(final String commandName,
                                        final String... commandTokens)
            throws BrobotCommandFormatException {

        final String[] ans = new String[commandTokens.length + 1];
        ans[0] = commandName;
        System.arraycopy(commandTokens, 0, ans, 1, commandTokens.length);

        return Task.createTask(ans);
    }

    private static final Task createTask(final String... commandTokens) throws BrobotDateFormatException {
        final BiFunction<Integer, Integer, String> stringJoiner = (final Integer startIdx, final Integer endIdx) -> {
            final String[] slice = new String[endIdx - startIdx];
            System.arraycopy(commandTokens, startIdx, slice, 0, endIdx - startIdx);

            return String.join(" ", slice);
        };

        if (commandTokens[0].equalsIgnoreCase("todo")) {
            return new ToDoTask(stringJoiner.apply(1, commandTokens.length), "ToDoTask");
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

            return new DeadlineTask(description, "DeadlineTask", deadline);
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

        return new EventTask(description, "EventTask", fromDate, toDate);
    }
}
