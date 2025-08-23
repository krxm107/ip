package brobot.tasks;

import brobot.BroBot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public final class TaskList {
    private final Path taskSavePath;

    private final ArrayList<Task> taskArrayList = new ArrayList<>();

    private static final TaskList taskListSingleton = new TaskList("./data/brobot tasks.txt");


    public static String printFormattedNumberedTask (final int number) {
        return String.format("%d. %s", number, TaskList.getTask(number));
    }

    private static void printSavedTasks() {
        if (TaskList.isEmpty()) {
            BroBot.delimit();
            System.out.println("You do not have any tasks saved from previous sessions.");
            BroBot.delimit();
            return;
        }

        BroBot.delimit();

        System.out.println("Here are the tasks saved from previous sessions.");
        for (int i = 1; i <= TaskList.size(); i++) {
            TaskList.printFormattedNumberedTask(i);
        }

        BroBot.delimit();
    }

    private TaskList (final String pathName) {
        this.taskSavePath = Paths.get(pathName);
        if (!Files.exists(this.taskSavePath)) {
            TaskList.printSavedTasks();
            return;
        }

        Scanner fileReader = null;
        boolean mustEnd = false;

        try {
            fileReader = new Scanner(this.taskSavePath);
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

            if (mustEnd) {
                System.exit(1);
            }
        }

        TaskList.printSavedTasks();
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

    private static void saveToFile() {
        try (final BufferedWriter fileSaveWriter = Files.newBufferedWriter(TaskList.taskListSingleton.taskSavePath)) {
            for (int i = 1; i <= TaskList.size(); i++) {
                fileSaveWriter.write(TaskList.getTask(i).toFileReport());
            }
        } catch (final IOException ioException) {
            BroBot.delimit();
            System.out.println("Oh no, the system has a problem writing the tasks to the hard disk.");
            System.out.println("Terminating program immediately");
            BroBot.delimit();
            System.exit(1);
        }
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
