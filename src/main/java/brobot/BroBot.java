// BroBot.java
package brobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.commands.ByeCommand;
import brobot.commands.Command;
import javafx.application.Platform;

public final class BroBot {
    public static final String FOUR_SPACES_INDENT = String.valueOf(new char[]{' ', ' ', ' ', ' '});

    private static BroBot singleton = null;

    private final List<FileIOStatus> loadMessages = new ArrayList<>();
    private BroBot() {
        FileIOStatus currStatus = Storage.getSingleton().readFromFile();
        loadMessages.add(currStatus);

        while (currStatus.checkIfFailure()) {
            currStatus = Storage.getSingleton().readFromFile();
            loadMessages.add(currStatus);
        }
    }

    public List<FileIOStatus> getLoadMessages() {
        return Collections.unmodifiableList(loadMessages);
    }

    public static BroBot getSingleton() {
        if (BroBot.singleton == null) {
            BroBot.singleton = new BroBot();
        }

        return BroBot.singleton;
    }

    public List<String> getResponses(String input) {
        try {
            Command c = Parser.parseCommand(input);
            if (c instanceof ByeCommand) {
                // allow GUI to close gracefully after showing the reply
                Platform.runLater(Platform::exit);
            }

            FileIOStatus result = c.sendBrobotMessage();
            final ArrayList<String> ans = new ArrayList<>();

            ans.add(result.toString());
            while (result.checkIfFailure()) {
                result = Storage.getSingleton().writeToFile();
                ans.add(result.toString());
            }

            assert List.copyOf(ans) != ans : "Sorry, the list returned should not be mutable.";
            return List.copyOf(ans);
        } catch (BrobotCommandFormatException e) {
            return List.of(e.sendBrobotMessage().toString());
        }
    }
}
