package brobot;

import brobot.tasks.Task;

import java.util.ArrayList;

public final class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private TaskList() {

    }

    private static TaskList taskListSingleton = null;
    public static TaskList getSingleton() {
        if (TaskList.taskListSingleton == null) {
            TaskList.taskListSingleton = new TaskList();
        }

        return TaskList.taskListSingleton;
    }

    public String printFormattedNumberedTask(final int number) {
        return TaskList.printFormattedNumberedTask(number, getTask(number));
    }

    public static String printFormattedNumberedTask(final int number, final Task task) {
        return String.format("%d. %s", number, task);
    }

    public Task getTask(final int number) {
        return tasks.get(number - 1);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    // TaskList mutator method, need to write to file.
    public void add(final Task task) {
        tasks.add(task);
        Storage.getSingleton().writeToFile();
    }

    // TaskList mutator method, need to write to file.
    public void remove(final int taskNumber) {
        tasks.remove(taskNumber - 1);
        Storage.getSingleton().writeToFile();
    }

    // TaskList mutator method, need to write to file.
    public void markTask(final int taskNumber) {
        getTask(taskNumber).mark();
        Storage.getSingleton().writeToFile();
    }

    // TaskList mutator method, need to write to file.
    public void unmarkTask(final int taskNumber) {
        getTask(taskNumber).unmark();
        Storage.getSingleton().writeToFile();
    }

    public void displayMessage(final Runnable emptyMessage, final Runnable nonEmptyMessage) {
        if (isEmpty()) {
            emptyMessage.run();
        } else {
            nonEmptyMessage.run();
        }
    }

    public void noTaskCheerOrElse(final Runnable orElse) {
        displayMessage(() -> System.out.print(this), orElse);
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "Enjoy your empty task list!\n";
        } else {
            final StringBuilder ans = new StringBuilder();
            for (int i = 1; i <= size(); i++) {
                ans.append(printFormattedNumberedTask(i) + "\n");
            }

            return ans.toString();
        }
    }
}
