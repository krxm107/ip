package brobot.tasks;

import brobot.brobotexceptions.BrobotCommandFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ToDoTest {
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

    @Test
    public void markToDoTest(){
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
