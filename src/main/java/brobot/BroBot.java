package brobot;

import brobot.task.Task;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

// Finished Level-0 to Level-4, as well as A-TextUiTesting.
public final class BroBot {
    /* private BroBot() {

    } */

    public static final String chatBotName = "BroBot";
    private static final ArrayList<Task> taskList = new ArrayList<>();
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
        final String command = inputReader.nextLine().strip().replaceAll("\\s+", " ");
        final String[] commandTokens = command.split(" ");

        if (command.equalsIgnoreCase("bye")) {
            BroBot.delimit();
            System.out.println("Bye. Hope to see you again soon!");
            BroBot.delimit();
            return false;
        }

        if (command.equalsIgnoreCase("list")) {
            BroBot.delimit();
            for (final Task task : taskList) {
                System.out.println(task);
            }

            BroBot.delimit();
            return true;
        }

        if (commandTokens.length == 2 && commandTokens[0].equalsIgnoreCase("mark")) {
            if (positiveIntegerPattern.matcher(commandTokens[1]).matches()) {
                int taskIndex = Integer.parseInt(commandTokens[1]) - 1;

                if (0 <= taskIndex && taskIndex < taskList.size()) {
                    BroBot.delimit();

                    BroBot.taskList.get(taskIndex).mark();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(BroBot.fourSpacesIndent + BroBot.taskList.get(taskIndex));

                    BroBot.delimit();
                    return true;
                } else {
                    BroBot.delimit();
                    BroBot.delimit();
                    return false;
                }
            } else {
                BroBot.delimit();
                BroBot.delimit();
                return false;
            }
        }

        if (commandTokens.length == 2 && commandTokens[0].equalsIgnoreCase("unmark")) {
            if (positiveIntegerPattern.matcher(commandTokens[1]).matches()) {
                int taskIndex = Integer.parseInt(commandTokens[1]) - 1;

                if (0 <= taskIndex && taskIndex < taskList.size()) {
                    BroBot.delimit();

                    BroBot.taskList.get(taskIndex).unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(BroBot.fourSpacesIndent + BroBot.taskList.get(taskIndex));

                    BroBot.delimit();

                    return true;
                } else {
                    BroBot.delimit();
                    BroBot.delimit();
                    return false;
                }
            } else {
                BroBot.delimit();
                BroBot.delimit();
                return false;
            }
        }

        if (commandTokens[0].equalsIgnoreCase("todo") ||
                commandTokens[0].equalsIgnoreCase("event") ||
            commandTokens[0].equalsIgnoreCase("deadline")) {
            BroBot.delimit();
            BroBot.taskList.add(Task.createTask(commandTokens));

            System.out.println("Got it. I've added this task:");
            System.out.println(BroBot.fourSpacesIndent + BroBot.taskList.get(BroBot.taskList.size() - 1));
            System.out.printf("Now you have %d tasks in the list.\n", BroBot.taskList.size());

            BroBot.delimit();
            return true;
        }

        BroBot.delimit();
        BroBot.delimit();
        return false;
    }

    public static void main (final String[] args) {
        BroBot.greet();
        while (BroBot.processCommand()) {

        }
    }
}
