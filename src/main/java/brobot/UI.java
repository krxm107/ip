package brobot;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.commands.ByeCommand;
import brobot.commands.Command;

import java.util.Scanner;
import java.util.function.Supplier;
import brobot.commands.Parser;
import brobot.tasks.Storage;
import brobot.tasks.TaskList;

public final class UI {
    public static final String fourSpacesIndent = String.valueOf(new char[]{' ', ' ', ' ', ' '});

    private static final Scanner inputScanner = new Scanner(System.in);

    private static final String chatBotName = "BroBot";

    private static void delimit() {
        System.out.println("____________________________________________________________");
    }

    public static void sendPrintMessage(final Runnable message) {
        UI.delimit();

        message.run();

        UI.delimit();
    }

    public static void performPrintAction (final Action action) {
        UI.sendPrintMessage(action.getAction());
    }

    private static void greet() {
        UI.sendPrintMessage(() -> {
            System.out.printf("Hello, I'm %s! What can I do for you?\n", UI.chatBotName);
            Storage.getSingleton().readFromFile();
        });
    }

    public static void main (final String[] args) {
        UI.greet();

        Command currCommand = null;
        while (currCommand != ByeCommand.getSingleton()) {
            try {
                final Supplier<String> inputLineReader = () -> {
                    final String inputLine = UI.inputScanner.nextLine();
                    return inputLine;
                };

                final String inputLine = inputLineReader.get();
                currCommand = Parser.parseCommand(inputLine);
                UI.performPrintAction(currCommand);
            } catch (final BrobotCommandFormatException badCommandFormat) {
                UI.performPrintAction(badCommandFormat);
            }
        }
    }
}
