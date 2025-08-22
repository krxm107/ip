package brobot.tasks;

import brobot.BroBot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

// TODO: Figure out how to READ from the file and print the saved tasks.
// TODO: Fix the bugs and make sure the mutator methods actually write to the file properly.
public final class TaskList {
    private final String pathName;
    private final ArrayList<Task> taskArrayList = new ArrayList<>();

    private static final TaskList taskListSingleton = new TaskList("./data/brobot tasks.txt");

    private TaskList (final String pathName) {
        this.pathName = pathName;
        Scanner fileReader = null;
        boolean mustEnd = false;

        try {
            final Path filePath = Paths.get(this.pathName);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            fileReader = new Scanner(filePath);
            final StringBuilder taskStringBuilder = new StringBuilder();

            while (fileReader.hasNextLine()) {
                final String taskLine = fileReader.nextLine();
                if (taskLine.isEmpty()) {
                    final String taskString = taskStringBuilder.deleteCharAt(taskStringBuilder.length() - 1).toString();
                    this.taskArrayList.add(Task.fromFileReport(taskString));
                } else {
                    taskStringBuilder.append(taskLine + "\n");
                }
            }
        } catch (final IOException ioException) {
            BroBot.delimit();
            System.out.println("Oh no, the system had a problem reading the file where your tasks were saved.");
            System.out.println("Terminating program immediately.");
            BroBot.delimit();
            mustEnd = true;
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }

        if (mustEnd) {
            System.exit(1);
        }
    }

    public static int size() {
        return TaskList.taskListSingleton.taskArrayList.size();
    }

    public static Task getTask (final int taskIndex) {
        return TaskList.taskListSingleton.taskArrayList.get(taskIndex - 1);
    }

    public static boolean isEmpty() {
        return TaskList.taskListSingleton.taskArrayList.isEmpty();
    }

    // TODO: Save tasks to the file.
    private static void saveToFile() {

    }

    // Mutator method, must save to file.
    public static void add (final Task task) {
        TaskList.taskListSingleton.taskArrayList.add(task);
        TaskList.saveToFile();
    }

    // Mutator method, must save to file.
    public static void remove (final int taskNumber) {
        TaskList.taskListSingleton.taskArrayList.remove(taskNumber - 1);
        TaskList.saveToFile();
    }


    // Mutator method, must save to file.
    public static void markTask (final int taskNumber) {
        TaskList.getTask(taskNumber).mark();
        TaskList.saveToFile();
    }

    // Mutator method, must save to file.
    public static void unmarkTask (final int taskNumber) {
        TaskList.getTask(taskNumber).unmark();
        TaskList.saveToFile();
    }
}
