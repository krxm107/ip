package brobot.tasks;

import java.util.ArrayList;

public final class TaskList {
    private final ArrayList<Task> taskArrayList = new ArrayList<>();
    private TaskList() {

    }

    private static TaskList taskListSingleton = null;
    public static TaskList getSingleton() {
        if (TaskList.taskListSingleton == null) {
            TaskList.taskListSingleton = new TaskList();
        }

        return TaskList.taskListSingleton;
    }

    public String printFormattedNumberedTask (final int number) {
        return this.printFormattedNumberedTask(number, this.getTask(number));
    }

    private String printFormattedNumberedTask (final int number, final Task task) {
        return String.format("%d. %s", number, task);
    }

    public Task getTask (final int number) {
        return this.taskArrayList.get(number - 1);
    }

    public int size() {
        return this.taskArrayList.size();
    }

    public boolean isEmpty() {
        return this.taskArrayList.isEmpty();
    }

    public void add (final Task task) {
        this.taskArrayList.add(task);
    }

    public void remove (final int taskNumber) {
        this.taskArrayList.remove(taskNumber - 1);
    }

    public void markTask (final int taskNumber) {
        this.getTask(taskNumber).mark();
    }

    public void unmarkTask (final int taskNumber) {
        this.getTask(taskNumber).unmark();
    }

    public void noTaskCheerOrElse (final Runnable orElse) {
        if (this.isEmpty()) {
            System.out.print(this);
        } else {
            orElse.run();
        }
    }

    @Override
    public String toString() {
        if (this.taskArrayList.isEmpty()) {
            return "Enjoy your empty task list!\n";
        } else {
            final StringBuilder ans = new StringBuilder();
            for (int i = 1; i <= this.size(); i++) {
                ans.append(this.printFormattedNumberedTask(i) + "\n");
            }

            return ans.toString();
        }
    }
}
