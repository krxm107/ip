package brobot;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.commands.ByeCommand;
import brobot.commands.Command;

import java.util.Scanner;
import java.util.function.Supplier;
import brobot.commands.Parser;
import brobot.tasks.Storage;

public final class BroBot {
    public static final String fourSpacesIndent = String.valueOf(new char[]{' ', ' ', ' ', ' '});

    private static final Scanner inputScanner = new Scanner(System.in);

    private static final String chatBotName = "BroBot";

    private static void delimit() {
        System.out.println("____________________________________________________________");
    }

    public static void sendPrintMessage(final Runnable message) {
        BroBot.delimit();

        message.run();

        BroBot.delimit();
    }

    public static void performPrintAction (final Action action) {
        BroBot.sendPrintMessage(action.getAction());
    }

    private static void greet() {
        BroBot.sendPrintMessage(() -> {
            System.out.printf("Hello, I'm %s! What can I do for you?\n", BroBot.chatBotName);
            Storage.getSingleton().readFromFile();
        });
    }

    public static void main (final String[] args) {
        BroBot.greet();

        Command currCommand = null;
        while (currCommand != ByeCommand.getSingleton()) {
            try {
                final Supplier<String> inputLineReader = () -> {
                    final String inputLine = BroBot.inputScanner.nextLine();
                    return inputLine;
                };

                final String inputLine = inputLineReader.get();
                currCommand = Parser.parseCommand(inputLine);
                BroBot.performPrintAction(currCommand);
            } catch (final BrobotCommandFormatException badCommandFormat) {
                BroBot.performPrintAction(badCommandFormat);
            }
        }
    }
}
