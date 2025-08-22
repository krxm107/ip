package brobot.tasks;

import brobot.brobotexceptions.InvalidDeadlineFormatException;
import brobot.brobotexceptions.InvalidEventFormatException;
import brobot.brobotexceptions.InvalidTODOFormatException;
import brobot.brobotexceptions.InvalidTaskFormatException;

import java.util.function.BiFunction;

public sealed abstract class Task permits Task.ToDo, Task.Deadline, Task.Event  {
    private static int nextFreeID = 1;

    private final String baseObjective;
    private final int id;
    private final String commandName;

    private Task (final String baseObjective, final String commandName) {
        this.id = Task.nextFreeID;
        this.baseObjective = baseObjective;
        this.commandName = commandName;
        Task.nextFreeID++;
    }

    @Override
    public final boolean equals (final Object o) {
        return this == o;
    }

    @Override
    public final int hashCode() {
        return this.id;
    }

    private String baseLogMessage = null;

    private boolean isDone = false;
    void mark() {
        this.isDone = true;
        this.baseLogMessage = null;
    }

    void unmark() {
        this.isDone = false;
        this.baseLogMessage = null;
    }

    @Override
    public String toString() {
        if (this.baseLogMessage == null) {
            this.baseLogMessage = String.format("[%c][%c] %s",
                    Character.toUpperCase(this.commandName.charAt(0)),
                    (this.isDone ? 'X' : ' '),
                    this.baseObjective);
        }

        return this.baseLogMessage;
    }

    String toFileReport() {
        return String.format("%s\n%s\n%s\n\n",
                            this.commandName,
                            this.isDone,
                            this.baseObjective);

    }

    static final Task fromFileReport (final String fileReport) {
        final String[] fileReportLines = fileReport.substring(0, fileReport.length() - 2).split("\n");

        switch (fileReportLines.length) {
            case 3: {
                final Task ans = new Task.ToDo(fileReportLines[2], fileReportLines[0]);
                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            case 4: {
                final Task ans = new Task.Deadline(fileReportLines[2], fileReportLines[0], fileReportLines[3]);
                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            case 5: {
                final Task ans = new Task.Event(fileReportLines[2], fileReportLines[0], fileReportLines[3], fileReportLines[4]);
                if (Boolean.parseBoolean(fileReportLines[1])) {
                    ans.mark();
                } else {
                    ans.unmark();
                }

                return ans;
            }

            default: {
                return null;
            }
        }
    }

    static final class ToDo extends Task {
        private ToDo (final String description, final String commandName) {
            super(description, commandName);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        String toFileReport() {
            return super.toFileReport();
        }
    }

    static final class Deadline extends Task {
        private final String deadline;
        private Deadline (final String description, final String commandName, final String deadline) {
            super(description, commandName);
            this.deadline = deadline;
        }

        private String deadlineLogMessage = null;

        @Override
        void mark() {
            super.mark();
            this.deadlineLogMessage = null;
        }

        @Override
        void unmark() {
            super.unmark();
            this.deadlineLogMessage = null;
        }

        @Override
        public String toString() {
            if (this.deadlineLogMessage == null) {
                this.deadlineLogMessage = String.format("%s (by: %s)", super.toString(), this.deadline);
            }

            return this.deadlineLogMessage;
        }

        @Override
        String toFileReport() {
            return String.format("%s\n%s\n\n", super.toFileReport().substring(0, super.toFileReport().length() - 2),
                                                this.deadline);
        }
    }

    static final class Event extends Task {
        private final String startDate, endDate;
        private Event (final String description, final String commandName,
                       final String startDate, final String endDate) {
            super(description, commandName);
            this.startDate = startDate;
            this.endDate = endDate;
        }

        private String eventlogMessage = null;
        @Override
        void mark() {
            super.mark();
            this.eventlogMessage = null;
        }

        @Override
        void unmark() {
            super.unmark();
            this.eventlogMessage = null;
        }

        @Override
        public String toString() {
            if (this.eventlogMessage == null) {
                this.eventlogMessage = String.format("%s (from: %s to: %s)", super.toString(), this.startDate, this.endDate);
            }

            return this.eventlogMessage;
        }

        @Override
        String toFileReport() {
            return String.format("%s\n%s\n%s\n\n",
                                 super.toFileReport().substring(0, super.toFileReport().length() - 2),
                                 this.startDate,
                                 this.endDate);
        }
    }

    public static final Task createTask (final String[] commandTokens) throws InvalidTaskFormatException {
        final BiFunction<Integer, Integer, String> stringJoiner = (final Integer startIdx, final Integer endIdx) -> {
            final String[] slice = new String[endIdx - startIdx];
            for (int i = startIdx; i < endIdx; i++) {
                slice[i - startIdx] = commandTokens[i];
            }

            return String.join(" ", slice);
        };

        if (commandTokens[0].equalsIgnoreCase("todo")) {
            if (commandTokens.length == 1) {
                throw InvalidTODOFormatException.newInvalidTODOFormatException();
            }

            return new Task.ToDo(stringJoiner.apply(1, commandTokens.length), "ToDo");
        }

        if (commandTokens[0].equalsIgnoreCase("deadline")) {
            int firstByIndex = -1;
            for (int i = 1; i < commandTokens.length; i++) {
                if (commandTokens[i].equalsIgnoreCase("by")) {
                    firstByIndex = i;
                    break;
                }
            }

            if (firstByIndex <= 0) {
                throw InvalidDeadlineFormatException.newInvalidDeadlineFormatException();
            }

            final String description = stringJoiner.apply(1, firstByIndex);
            final String deadline = stringJoiner.apply(firstByIndex + 1, commandTokens.length);

            return new Task.Deadline(description, "Deadline", deadline);
        }

        int firstFromIndex = -1;
        for (int i = 1; i < commandTokens.length; i++) {
            if (commandTokens[i].equalsIgnoreCase("from")) {
                firstFromIndex = i;
                break;
            }
        }

        if (firstFromIndex <= 0) {
            throw InvalidEventFormatException.newInvalidEventFormatException();
        }

        int firstToIndex = firstFromIndex - 1;
        for (int i = firstFromIndex + 1; i < commandTokens.length; i++) {
            if (commandTokens[i].equalsIgnoreCase("to")) {
                firstToIndex = i;
                break;
            }
        }

        if (firstToIndex <= firstFromIndex) {
            throw InvalidEventFormatException.newInvalidEventFormatException();
        }

        final String description = stringJoiner.apply(1, firstFromIndex);
        final String fromDate = stringJoiner.apply(firstFromIndex + 1, firstToIndex);
        final String toDate = stringJoiner.apply(firstToIndex + 1, commandTokens.length);

        return new Task.Event(description, "Event", fromDate, toDate);
    }
}