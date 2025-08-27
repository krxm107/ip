package brobot.tasks;

import brobot.brobotexceptions.BrobotCommandFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class TaskListTest {
    private void clearTaskList() {
        while (!TaskList.getSingleton().isEmpty()) {
            TaskList.getSingleton().remove(TaskList.getSingleton().size());
        }
    }

    @Test
    public void testTaskListSingleton() {
        this.clearTaskList();
        assertTrue(TaskList.getSingleton() == TaskList.getSingleton());
    }

    @Test
    public void testTaskListSingletonEmpty() {
        this.clearTaskList();
        assertTrue(TaskList.getSingleton().isEmpty());
    }

    @Test
    public void testTaskListSingletonSize0() {
        this.clearTaskList();
        assertEquals(TaskList.getSingleton().size(), 0);
    }

    @Test
    public void testAddTask() {
        this.clearTaskList();
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
        this.clearTaskList();
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
