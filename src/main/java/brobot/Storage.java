package brobot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import brobot.tasks.Task;

/**
 * Specializes in File IO
 */
public final class Storage {

    private static Storage storageSingleton = null;
    private final Path taskSavePath = Paths.get("./data/brobot tasks.txt");

    private Storage() {

    }

    /**
     * Lazy factory method constructor
     * @return The necessary singleton Storage instance
     */
    public static Storage getSingleton() {
        if (Storage.storageSingleton == null) {
            Storage.storageSingleton = new Storage();
        }

        return Storage.storageSingleton;
    }

    /**
     * Reads from the "data/brobot tasks.txt" file and adds the saved tasks to the TaskList singleton
     */
    public void readFromFile() {
        Scanner fileReader = null;
        boolean mustExit = false;

        try {
            if (!Files.exists(taskSavePath.getParent())) {
                Files.createDirectories(taskSavePath.getParent());
            }

            fileReader = new Scanner(taskSavePath);

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

            TaskList.getSingleton().displayMessage(() ->
                            System.out.println("You do not have any tasks saved from previous sessions."), () -> {
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

    /**
     * Writes the Tasks in the TaskList singleton to the "data/brobot tasks.txt" file in the hard disk.
     * <p>
     * Please make sure to manually call this method every time the Tasklist singleton is modified
     * so that the tasks can be saved to the hard disk. This is a safety precaution in the event of program failure.
     */
    public void writeToFile() {
        Path path = taskSavePath;
        File file = path.toFile();

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (final FileWriter fileSaveWriter = new FileWriter(taskSavePath.toFile())) {
            for (int i = 1; i <= TaskList.getSingleton().size(); i++) {
                fileSaveWriter.write(TaskList.getSingleton().getTask(i).toFileReport());
            }
        } catch (final IOException ioException) {
            BroBot.sendPrintMessage(() -> {
                System.out.println("Oh no, the system has a problem "
                        + "writing the tasks to the hard disk.");

                System.out.println("Terminating program immediately.");
            });

            System.exit(1);
        }
    }
}
