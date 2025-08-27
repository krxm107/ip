package brobot.tasks;

import brobot.BroBot;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public final class Storage {

    private final Path taskSavePath = Paths.get("./data/brobot tasks.txt");
    private Storage() {

    }

    private static Storage storageSingleton = null;
    public static Storage getSingleton() {
        if (Storage.storageSingleton == null) {
            Storage.storageSingleton = new Storage();
        }

        return Storage.storageSingleton;
    }

    public void readFromFile() {
        Scanner fileReader = null;
        boolean mustExit = false;

        try {
            if (!Files.exists(this.taskSavePath.getParent())) {
                Files.createDirectories(this.taskSavePath.getParent());
            }

            fileReader = new Scanner(this.taskSavePath);

            final StringBuilder taskStringBuilder = new StringBuilder();
            while (fileReader.hasNextLine()) {
                final String taskLine = fileReader.nextLine();
                if (taskLine.isEmpty()) {
                    final String taskString = taskStringBuilder.deleteCharAt(taskStringBuilder.length() - 1).toString();
                    TaskList.getSingleton().add(Task.fromFileReport(taskString));
                    while (!taskStringBuilder.isEmpty()) {
                        taskStringBuilder.deleteCharAt(taskStringBuilder.length() - 1);
                    }
                } else {
                    taskStringBuilder.append(taskLine + "\n");
                }
            }

            TaskList.getSingleton().displayMessage(() -> System.out.println("You do not have any tasks saved from previous sessions."),
                    () -> {
                        System.out.println("Here are the tasks saved from previous sessions.");
                        System.out.print(TaskList.getSingleton());
                    });

        } catch (final NoSuchFileException noFileYet) {
            TaskList.getSingleton().displayMessage(() -> {
                System.out.println("You do not have any tasks saved from previous sessions.");
            }, () -> {
                System.out.println("You do not have any tasks saved from previous sessions.");
            });
        } catch (final IOException ioException) {
            TaskList.getSingleton().displayMessage(() -> {
                System.out.println("Oh no, the system had a problem reading the file where your tasks were saved.");
                System.out.println("Terminating program immediately.");
            }, () -> {
                System.out.println("Oh no, the system had a problem reading the file where your tasks were saved.");
                System.out.println("Terminating program immediately.");
            });

            mustExit = true;
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }

            if (mustExit) {
                System.exit(1);
            }
        }
    }

    public void writeToFile() {
        try (final FileWriter fileSaveWriter = new FileWriter(this.taskSavePath.toFile())) {
            for (int i = 1; i <= TaskList.getSingleton().size(); i++) {
                fileSaveWriter.write(TaskList.getSingleton().getTask(i).toFileReport());
            }
        } catch (final IOException ioException) {
            BroBot.sendPrintMessage(() -> {
                System.out.println("Oh no, the system has a problem writing the tasks to the hard disk.");
                System.out.println("Terminating program immediately.");
            });

            System.exit(1);
        }
    }
}
