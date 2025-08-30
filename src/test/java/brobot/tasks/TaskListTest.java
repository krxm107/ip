package brobot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import brobot.TaskList;
import brobot.brobotexceptions.BrobotCommandFormatException;

public final class TaskListTest {
    private void clearTaskList() {
        while (!TaskList.getSingleton().isEmpty()) {
            TaskList.getSingleton().remove(TaskList.getSingleton().size());
        }
    }

    @Test
    public void testTaskListSingleton() {
        clearTaskList();
        assertTrue(TaskList.getSingleton() == TaskList.getSingleton());
    }

    @Test
    public void testTaskListSingletonEmpty() {
        clearTaskList();
        assertTrue(TaskList.getSingleton().isEmpty());
    }

    @Test
    public void testTaskListSingletonSize0() {
        clearTaskList();
        assertEquals(TaskList.getSingleton().size(), 0);
    }

    @Test
    public void testAddTask() {
        clearTaskList();
        try {
            TaskList.getSingleton().add(Task.createTask("todo", "add task"));

            final String expectedToString = "1. [T][ ] add task\n";
            assertEquals(expectedToString, TaskList.getSingleton().toString());
        } catch (final BrobotCommandFormatException brobotCommandFormatException) {
            assert false;
        }
    }

    @Test
    public void testRemoveTask() {
        clearTaskList();
        try {
            TaskList.getSingleton().add(Task.createTask("todo", "task 0"));
            TaskList.getSingleton().add(Task.createTask("todo", "task to remove"));

            TaskList.getSingleton().remove(2);

            final String expectedToString = "1. [T][ ] task 0\n";
            assertEquals(expectedToString, TaskList.getSingleton().toString());
        } catch (final BrobotCommandFormatException brobotCommandFormatException) {
            assert false;
        }
    }
}
