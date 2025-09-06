package brobot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import brobot.tasks.Task;

/**
 * Specializes in File IO
 */
public final class Storage {
    private static Storage storageSingleton = null;
    private final Path taskSavePath = Paths.get("./data/brobot tasks.txt");

    public static final String SUCCESSFUL_HARD_DRIVE_SAVE = "Your tasks have successfully been saved to the hard drive.";

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
    public FileIOStatus readFromFile() {
        Scanner fileReader = null;

        try {
            if (!Files.exists(taskSavePath.getParent())) {
                Files.createDirectories(taskSavePath.getParent());
            }

            if (Files.notExists(taskSavePath)) {
                Files.createFile(taskSavePath);
            }

            fileReader = new Scanner(taskSavePath);

            final StringBuilder taskStringBuilder = new StringBuilder();
            while (fileReader.hasNextLine()) {
                final String taskLine = fileReader.nextLine();
                if (taskLine.isEmpty()) {
                    final String taskString = taskStringBuilder.deleteCharAt(taskStringBuilder.length() - 1).toString();
                    TaskList.getSingleton().addToTaskList(Task.fromFileReport(taskString));
                    while (!taskStringBuilder.isEmpty()) {
                        taskStringBuilder.deleteCharAt(taskStringBuilder.length() - 1);
                    }
                } else {
                    taskStringBuilder.append(taskLine + System.lineSeparator());
                }
            }

            return FileIOStatus.makeSuccessStatus(TaskList.getSingleton().displayMessage(() ->
                            FileIOStatus.makeSuccessStatus("You do not have any tasks saved from previous sessions."), () -> {
                        final String line1 = "Here are the tasks saved from previous sessions.";
                        final String line2 = TaskList.getSingleton().toString();
                        return FileIOStatus.makeSuccessStatus(String.join(System.lineSeparator(), line1, line2));
                }));

        } catch (final IOException ioException) {
            return FileIOStatus.makeFailureStatus(TaskList.getSingleton().displayMessage(() -> {
                final String line1 = "Oh no, the system had a problem reading the file where your tasks were saved.";
                final String line2 = "Trying again. Hang in there.";

                return FileIOStatus.makeFailureStatus(String.join(System.lineSeparator(), line1, line2));
            }, () -> {
                final String line1 = "Oh no, the system had a problem reading the file where your tasks were saved.";
                final String line2 = "Trying again. Hang in there.";
                return FileIOStatus.makeFailureStatus(String.join(System.lineSeparator(), line1, line2));
            }));
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }

    /**
     * Writes the Tasks in the TaskList singleton to the "data/brobot tasks.txt" file in the hard disk.
     * <p>
     * Please make sure to manually call this method every time the Tasklist singleton is modified
     * so that the tasks can be saved to the hard disk. This is a safety precaution in the event of program failure.
     */
    public FileIOStatus writeToFile() {
        try {
            Path path = taskSavePath;
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )) {
                for (int i = 1; i <= TaskList.getSingleton().getSize(); i++) {
                    writer.write(TaskList.getSingleton().getTask(i).toFileReport());
                }
            }

            return FileIOStatus.makeSuccessStatus(Storage.SUCCESSFUL_HARD_DRIVE_SAVE);
        } catch (IOException e) {
            return FileIOStatus.makeFailureStatus(
                    String.join("Oh no, the system has a problem writing the tasks to the hard disk.",
                            "Trying again. Hang in there.",
                                 System.lineSeparator())
            );
        }
    }
}
