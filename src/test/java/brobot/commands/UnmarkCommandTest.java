package brobot.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import brobot.Parser;
import brobot.StatefulTest;
import brobot.TaskList;
import brobot.TaskListHelper;
import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.tasks.Task;

public final class UnmarkCommandTest implements StatefulTest {

    /**
     * Test unmarking any random order of tasks (have tasks remain).
     */
    @Test
    public void testUnmarkInRandomOrder() {
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

            TaskListHelper.getAllElems().forEach(Task::mark);

            final Command unmarkRandomTasksCommand = Parser.parseCommand("unmark 5 1 3");

            unmarkRandomTasksCommand.sendBrobotMessage();

            final List<String> actualStringReps = TaskListHelper.getAllElems().stream().map(Task::toString).toList();
            final List<String> expectedStringReps = List.of("[T][ ] 1", "[T][X] 2", "[T][ ] 3", "[T][X] 4", "[T][ ] 5");

            assertEquals(actualStringReps, expectedStringReps);
        } catch (final BrobotCommandFormatException e) {
            assert false;
        }
    }


    /**
     * Test unmarking all tasks
     */
    @Test
    public void testUnmarkAllTasks() {
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

            TaskListHelper.getAllElems().forEach(Task::mark);


            final Command unmarkAllTasksCommand = Parser.parseCommand("unmark all");

            unmarkAllTasksCommand.sendBrobotMessage();

            final List<String> actualStringReps = TaskListHelper.getAllElems().stream().map(Task::toString).toList();
            final List<String> expectedStringReps = List.of("[T][ ] 1", "[T][ ] 2", "[T][ ] 3", "[T][ ] 4", "[T][ ] 5");

            assertEquals(actualStringReps, expectedStringReps);
        } catch (final BrobotCommandFormatException e) {
            assert false;
        }
    }
}
