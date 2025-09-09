package brobot;

import java.util.ArrayList;

import brobot.tasks.Task;

/**
 * This class specializes in storing tasks from the user.
 */
public final class TaskList {

    private static TaskList taskListSingleton = null;

    private final ArrayList<Task> tasks = new ArrayList<>();

    private TaskList() {

    }

    /**
     * Lazy factory constructor.
     *
     * @return
     *     The TaskList singleton instance.
     */
    public static TaskList getSingleton() {
        if (TaskList.taskListSingleton == null) {
            TaskList.taskListSingleton = new TaskList();
        }

        return TaskList.taskListSingleton;
    }

    /**
     * Returns a string for printing the task with the given number, labelled with that number.
     */
    public String formatTask(final int number) {
        return String.format(BroBot.ENGLISH_LANGUAGE, "%d. %s", number, getTask(number));
    }

    /**
     * Gets the ith task (1-indexed).
     */
    public Task getTask(final int number) {
        return tasks.get(number - 1);
    }

    /**
     * @return The number of tasks in the TaskList.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * @return A boolean indicating whether the tasklist is empty (i.e. whether the tasklist has no tasks in it).
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * @param task
     *     Task to be added to the end of the TaskList.
     *
     *     Once the task is added to the tasklist, the tasks are saved to the hard drive.
     */
    public FileIoStatus addToTaskList(final Task task) {
        tasks.add(task);
        return Storage.getSingleton().writeToFile();
    }

    /**
     * @param taskNumber
     *     The number of the task that must be removed (1-indexed).
     */
    public FileIoStatus removeFromTaskList(final int taskNumber) {
        tasks.remove(taskNumber - 1);
        return Storage.getSingleton().writeToFile();
    }

    /**
     * @param taskNumber
     *     The number of the task that must be marked (1-indexed).
     */
    public FileIoStatus markTask(final int taskNumber) {
        getTask(taskNumber).mark();
        return Storage.getSingleton().writeToFile();
    }

    /**
     * @param taskNumber
     *     The number of the task that must be unmarked (1-indexed).
     */
    public FileIoStatus unmarkTask(final int taskNumber) {
        getTask(taskNumber).unmark();
        return Storage.getSingleton().writeToFile();
    }

    /**
     * @param emptyMessage
     *     The message to print if the TaskList is empty.
     *
     * @param nonEmptyMessage
     *     The message to print if the TaskList is not empty.
     */
    public String displayMessage(final BrobotMessenger emptyMessage, final BrobotMessenger nonEmptyMessage) {
        if (isEmpty()) {
            return emptyMessage.sendBrobotMessage().toString();
        } else {
            return nonEmptyMessage.sendBrobotMessage().toString();
        }
    }

    /**
     * @param orElse
     *     The message to print if the tasklist is not empty.
     */
    public String noTaskCheerOrElse(final BrobotMessenger orElse) {
        return displayMessage(() -> FileIoStatus.makeSuccessStatus(toString()), orElse);
    }

    /**
     * @return
     *     The empty task cheer if the TaskList is empty, or the numbered tasks in the task list otherwise.
     */
    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "Enjoy your empty task list!";
        } else {
            final StringBuilder ans = new StringBuilder();
            for (int i = 1; i <= getSize(); i++) {
                ans.append(formatTask(i)).append(System.lineSeparator());
            }

            return StringNewlineFormatter.removeTrailingNewlines(ans, 1);
        }
    }
}
