package brobot.commands;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import brobot.Parser;
import brobot.StatefulTest;
import brobot.TaskList;
import brobot.TaskListHelper;
import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.tasks.Task;

/**
 * Tests mark command for the bug where tasks are deleted in any order (MassOps)
 */
public final class MarkCommandTest implements StatefulTest {

    /**
     * Test marking any random order of tasks (have tasks remain).
     */
    @Test
    public void testMarkInRandomOrder() {
        try {
            final Task task1 = Task.createTask("todo", "1");
            final Task task2 = Task.createTask("todo", "2");
            final Task task3 = Task.createTask("todo", "3");
            final Task task4 = Task.createTask("todo", "4");
            final Task task5 = Task.createTask("todo", "5");

            TaskList.getSingleton().addToTaskList(task1);
            TaskList.getSingleton().addToTaskList(task2);
            TaskList.getSingleton().addToTaskList(task3);
            TaskList.getSingleton().addToTaskList(task4);
            TaskList.getSingleton().addToTaskList(task5);

            final Command markRandomTasksCommand = Parser.parseCommand("mark 5 1 3");

            markRandomTasksCommand.sendBrobotMessage();

            final List<String> actualStringReps = TaskListHelper.getAllElems().stream().map(Task::toString).toList();
            final List<String> expectedStringReps = List.of("[T][X] 1", "[T][ ] 2", "[T][X] 3", "[T][ ] 4", "[T][X] 5");

            assertEquals(actualStringReps, expectedStringReps);
        } catch (final BrobotCommandFormatException e) {
            assert false;
        }
    }


    /**
     * Test marking all tasks
     */
    @Test
    public void testMarkAllTasks() {
        try {
            final Task task1 = Task.createTask("todo", "1");
            final Task task2 = Task.createTask("todo", "2");
            final Task task3 = Task.createTask("todo", "3");
            final Task task4 = Task.createTask("todo", "4");
            final Task task5 = Task.createTask("todo", "5");

            TaskList.getSingleton().addToTaskList(task1);
            TaskList.getSingleton().addToTaskList(task2);
            TaskList.getSingleton().addToTaskList(task3);
            TaskList.getSingleton().addToTaskList(task4);
            TaskList.getSingleton().addToTaskList(task5);

            final Command markAllTasksCommand = Parser.parseCommand("mark all");

            markAllTasksCommand.sendBrobotMessage();

            final List<String> actualStringReps = TaskListHelper.getAllElems().stream().map(Task::toString).toList();
            final List<String> expectedStringReps = List.of("[T][X] 1", "[T][X] 2", "[T][X] 3", "[T][X] 4", "[T][X] 5");

            assertEquals(actualStringReps, expectedStringReps);
        } catch (final BrobotCommandFormatException e) {
            assert false;
        }
    }
}
