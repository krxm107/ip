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
    public String printFormattedNumberedTask(final int number) {
        return TaskList.printFormattedNumberedTask(number, getTask(number));
    }

    /**
     * Returns a string for printing a task with a custom number label.
     */
    public static String printFormattedNumberedTask(final int number, final Task task) {
        return String.format("%d. %s", number, task);
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
    public int size() {
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
    public void add(final Task task) {
        tasks.add(task);
        Storage.getSingleton().writeToFile();
    }

    /**
     * @param taskNumber
     *     The number of the task that must be removed (1-indexed).
     */
    public void remove(final int taskNumber) {
        tasks.remove(taskNumber - 1);
        Storage.getSingleton().writeToFile();
    }

    /**
     * @param taskNumber
     *     The number of the task that must be marked (1-indexed).
     */
    public void markTask(final int taskNumber) {
        getTask(taskNumber).mark();
        Storage.getSingleton().writeToFile();
    }

    /**
     * @param taskNumber
     *     The number of the task that must be unmarked (1-indexed).
     */
    public void unmarkTask(final int taskNumber) {
        getTask(taskNumber).unmark();
        Storage.getSingleton().writeToFile();
    }

    /**
     * @param emptyMessage
     *     The message to print if the TaskList is empty.
     *
     * @param nonEmptyMessage
     *     The message to print if the TaskList is not empty.
     */
    public void displayMessage(final BrobotAction emptyMessage, final BrobotAction nonEmptyMessage) {
        if (isEmpty()) {
            emptyMessage.performBrobotAction();
        } else {
            nonEmptyMessage.performBrobotAction();
        }
    }

    /**
     * @param orElse
     *     The message to print if the tasklist is not empty.
     */
    public void noTaskCheerOrElse(final BrobotAction orElse) {
        displayMessage(() -> System.out.print(this), orElse);
    }

    /**
     * @return
     *     The empty task cheer if the TaskList is empty, or the numbered tasks in the task list otherwise.
     */
    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "Enjoy your empty task list!\n";
        } else {
            final StringBuilder ans = new StringBuilder();
            for (int i = 1; i <= size(); i++) {
                ans.append(printFormattedNumberedTask(i)).append("\n");
            }

            return ans.toString();
        }
    }
}
