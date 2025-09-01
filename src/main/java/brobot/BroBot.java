// BroBot.java
package brobot;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.commands.ByeCommand;
import brobot.commands.Command;
import javafx.application.Platform;

public final class BroBot {
    public static final String FOUR_SPACES_INDENT = String.valueOf(new char[]{' ', ' ', ' ', ' '});
    public String getResponse(String input) {
        try {
            Command c = Parser.parseCommand(input);
            final FileIOStatus result = c.sendBrobotMessage();
            if (c instanceof ByeCommand) {
                // allow GUI to close gracefully after showing the reply
                Platform.runLater(Platform::exit);
            }

            if (result.checkIfFailure()) {
                Platform.runLater(Platform::exit);
                return result.toString();
            } else {
                return c.sendBrobotMessage().toString();
            }
        } catch (BrobotCommandFormatException e) {
            return e.sendBrobotMessage().toString();
        }
    }
}
