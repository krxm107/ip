package brobot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import brobot.brobotexceptions.BrobotCommandFormatException;

/**
 * Tests The ToDoTask class
 */
public final class ToDoTaskTest {
    /**
     * Simple unit test of toString for creating a new ToDoTask.
     */
    @Test
    public void makeNewTaskTest() {
        try {
            final Task test = Task.createTask("todo", "test dummy");

            final String expectedToString = "[T][ ] test dummy";
            assertEquals(expectedToString, test.toString());
        } catch (BrobotCommandFormatException e) {
            assert(false);
        }
    }

    /**
     * Simple test of toString for creating a new ToDoTask, then marking it as done.
     */
    @Test
    public void markToDoTest() {
        try {
            final Task test = Task.createTask("todo", "test dummy");

            test.mark();

            final String expectedToString = "[T][X] test dummy";
            assertEquals(expectedToString, test.toString());
        } catch (BrobotCommandFormatException e) {
            assert(false);
        }
    }
}
