package brobot;

import java.util.List;
import java.util.stream.IntStream;

import brobot.tasks.Task;

/**
 * Allows custom tasklist operations for testing purposes.
 */
public final class TaskListHelper {
    private TaskListHelper() {

    }

    /**
     * Clears the task list.
     */
    public static void clearTaskList() {
        while (!TaskList.getSingleton().isEmpty()) {
            TaskList.getSingleton().removeFromTaskList(TaskList.getSingleton().getSize());
        }
    }

    /**
     * Gets all the elements in the task list
     */
    public static List<Task> getAllElems() {
        return IntStream.rangeClosed(1, TaskList.getSingleton().getSize())
                        .mapToObj(TaskList.getSingleton()::getTask)
                        .toList();
    }
}
