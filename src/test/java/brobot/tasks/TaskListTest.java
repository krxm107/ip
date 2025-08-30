package brobot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import brobot.StatefulTest;
import brobot.TaskList;
import brobot.brobotexceptions.BrobotCommandFormatException;

/**
 * Unit Test Class for TaskList.
 */
public final class TaskListTest implements StatefulTest {
    /**
     * Simple Unit Test to test that the public TaskList contract only creates a singleton.
     */
    @Test
    public void testTaskListSingleton() {
        assertSame(TaskList.getSingleton(), TaskList.getSingleton());
    }

    /**
     * Check whether the singleton is empty on creation.
     */
    @Test
    public void testTaskListSingletonEmpty() {
        assertTrue(TaskList.getSingleton().isEmpty());
    }

    /**
     * Check whether the singleton's size is 0 on creation.
     */
    @Test
    public void testTaskListSingletonSize0() {
        assertEquals(0, TaskList.getSingleton().size());
    }

    /**
     * Test the toString method when adding a single task to the singleton.
     */
    @Test
    public void testAddTask() {
        try {
            TaskList.getSingleton().add(Task.createTask("todo", "add task"));

            final String expectedToString = "1. [T][ ] add task\n";
            assertEquals(expectedToString, TaskList.getSingleton().toString());
        } catch (final BrobotCommandFormatException brobotCommandFormatException) {
            assert false;
        }
    }

    /**
     * Test the numbering system when a task is removed from the TaskList, and it's not at the very back.
     */
    @Test
    public void testRemoveTask() {
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
