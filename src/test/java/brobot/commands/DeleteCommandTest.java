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

public final class DeleteCommandTest implements StatefulTest {

    /**
     * Test deleting any random order of tasks (have tasks remain).
     */
    @Test
    public void testDeleteInRandomOrder() {
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

            final Command deleteRandomTasksCommand = Parser.parseCommand("delete 5 1 3");

            deleteRandomTasksCommand.sendBrobotMessage();

            assertEquals(TaskListHelper.getAllElems(), List.of(task2, task4));
        } catch (final BrobotCommandFormatException e) {
            assert false;
        }
    }


    /**
     * Test deleting all tasks
     */
    @Test
    public void testDeleteAllTasks() {
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


            final Command unmarkAllTasksCommand = Parser.parseCommand("delete all");

            unmarkAllTasksCommand.sendBrobotMessage();

            final List<String> actualStringReps = TaskListHelper.getAllElems().stream().map(Task::toString).toList();
            final List<String> expectedStringReps = List.of();

            assertEquals(actualStringReps, expectedStringReps);
        } catch (final BrobotCommandFormatException e) {
            assert false;
        }
    }
}
