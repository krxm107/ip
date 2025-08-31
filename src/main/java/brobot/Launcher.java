package brobot;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public final class Launcher {
    public static void main(String[] args) {
        Application.launch(brobot.Main.class, args);
    }
}
