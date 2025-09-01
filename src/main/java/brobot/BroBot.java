// BroBot.java
package brobot;

import brobot.brobotexceptions.BrobotCheckedException;
import brobot.commands.ByeCommand;
import brobot.commands.Command;
import javafx.application.Platform;

public final class BroBot {
    public static final String FOUR_SPACES_INDENT = String.valueOf(new char[]{' ', ' ', ' ', ' '});
    public String getResponse(String input) {
        try {
            Command c = Parser.parseCommand(input);
            String result = c.sendMessage();
            if (c instanceof ByeCommand) {
                // allow GUI to close gracefully after showing the reply
                Platform.runLater(Platform::exit);
            }
            return result;
        } catch (BrobotCheckedException e) {
            // Show a friendly message in the dialog instead of crashing
            return e.getMessage();
        } catch (Exception e) {
            return "Oops, something went wrong: " + e.getMessage();
        }
    }
}
