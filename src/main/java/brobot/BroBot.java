package brobot;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.commands.ByeCommand;
import brobot.commands.Command;

import java.util.Scanner;
import java.util.function.Supplier;

/**
 * The main BroBot UI class. Its main method is the sole entry point of the system.
 */
public final class BroBot {
    public static final String FOUR_SPACES_INDENT = String.valueOf(new char[]{' ', ' ', ' ', ' '});

    private static final Scanner inputScanner = new Scanner(System.in);

    private static final String CHAT_BOT_NAME = "BroBot";

    private static void delimit() {
        System.out.println("____________________________________________________________");
    }

    /**
     * @param message
     * Allows the relavant classes to send messages to the BroBot UI class for messages to be printed out.
     */
    public static void sendPrintMessage(final Runnable message) {
        BroBot.delimit();

        message.run();

        BroBot.delimit();
    }

    private static void greet() {
        BroBot.sendPrintMessage(() -> {
            System.out.printf("Hello, I'm %s! What can I do for you?\n", BroBot.CHAT_BOT_NAME);
            Storage.getSingleton().readFromFile();
        });
    }

    /**
     * The sole entry point of the BroBot program.
     */
    public static void main(final String[] args) {
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
                BroBot.sendPrintMessage(currCommand);
            } catch (final BrobotCommandFormatException badCommandFormat) {
                BroBot.sendPrintMessage(badCommandFormat);
            }
        }
    }
}
