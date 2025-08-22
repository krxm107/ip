package brobot.filehandlingcode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public final class FileIOCode {
    private static final Path taskSavePath = Paths.get("data", "brobottasks.txt");

    private FileIOCode() {

    }
}
