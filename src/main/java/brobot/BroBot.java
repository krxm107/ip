package brobot;

import brobot.brobotexceptions.commandexceptions.BrobotCommandException;
import brobot.brobotexceptions.commandexceptions.EmptyCommandException;
import brobot.brobotexceptions.commandexceptions.NoSuchCommandNameException;
import brobot.task.Task;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

// Finished Level-0 to Level-4, as well as A-TextUiTesting.
public final class BroBot {
    private BroBot() {

    }

    public static final String chatBotName = "BroBot";

    private static final ArrayList<Task> taskList = new ArrayList<>();
    private static String getNumberedTask (final int number) {
        return String.format("%d. %s", number, BroBot.taskList.get(number - 1));
    }

    private static final String fourSpacesIndent = String.valueOf(new char[]{' ', ' ', ' ', ' '});
    
    private static void greet() {
        BroBot.delimit();
        System.out.printf("Hello, I'm %s! What can I do for you?\n", BroBot.chatBotName);
        BroBot.delimit();
    }

    private static void delimit() {
        System.out.println("____________________________________________________________");
    }

    private static final Scanner inputReader = new Scanner(System.in);
    private static final Pattern positiveIntegerPattern = Pattern.compile("^[1-9]\\d*$");

    private static boolean processCommand() {
        // TODO: Rethink the way I process commands using this method. Take into account exceptions by catching them all here.
        try {
            final String commandText = inputReader.nextLine().strip().replaceAll("\\s+", " ");
            if (commandText.isEmpty()) {
                throw new EmptyCommandException();
            }

            final String[] commandTokens = commandText.split(" ");
        } catch (final BrobotCommandException brobotCommandException) {
            BroBot.delimit();
            System.out.println(brobotCommandException.getMessage());
            BroBot.delimit();
            return true;
        }

        return true;
    }

    public static void main (final String[] args) {
        BroBot.greet();
        while (BroBot.processCommand()) {

        }
    }
}
