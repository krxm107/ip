package brobot;

import brobot.brobotexceptions.*;
import brobot.commands.Command;
import brobot.tasks.Task;
import brobot.tasks.TaskList;

import java.util.Scanner;
import java.util.regex.Pattern;

// Finished Level-0 to Level-4, as well as A-TextUiTesting.
public final class BroBot {
    private BroBot() {

    }

    public static final String chatBotName = "BroBot";

    private static final String fourSpacesIndent = String.valueOf(new char[]{' ', ' ', ' ', ' '});
    
    private static void greet() {
        BroBot.delimit();
        System.out.printf("Hello, I'm %s! What can I do for you?\n", BroBot.chatBotName);
        BroBot.delimit();
    }

    public static void delimit() {
        System.out.println("____________________________________________________________");
    }

    private static final Scanner inputReader = new Scanner(System.in);
    private static final Pattern positiveIntegerPattern = Pattern.compile("^[1-9]\\d*$");

    private static boolean processCommand() {
        try {
            final String commandText = inputReader.nextLine().strip().replaceAll("\\s+", " ");
            if (commandText.isEmpty()) {
                throw new EmptyCommandException();
            }

            final String[] commandTokens = commandText.split(" ");
            final Command command = Command.fromNameIgnoreCase(commandTokens[0]);

            switch (command) {
                case BYE: {
                    if (commandTokens.length != 1) {
                        throw SomeArgsLeftException.fromCommandName(command.getName());
                    }

                    BroBot.delimit();
                    System.out.println("Bye. Hope to see you again soon!");
                    BroBot.delimit();

                    return false;
                }

                case LIST: {
                    if (commandTokens.length != 1) {
                        throw SomeArgsLeftException.fromCommandName(command.getName());
                    }

                    if (TaskList.isEmpty()) {
                        BroBot.delimit();
                        System.out.println("Enjoy your empty task list!");
                        BroBot.delimit();
                        return true;
                    }

                    BroBot.delimit();
                    for (int idx = 1; idx <= TaskList.size(); idx++) {
                        System.out.println(TaskList.printFormattedNumberedTask(idx));
                    }
                    BroBot.delimit();

                    return true;
                }

                case MARK: {
                    if (commandTokens.length != 2) {
                        throw MissingNumberException.fromCommandName(commandTokens[0]);
                    }

                    final int taskIndex = Integer.parseInt(commandTokens[1]);
                    if (1 <= taskIndex && taskIndex <= TaskList.size()) {
                        BroBot.delimit();

                        TaskList.markTask(taskIndex);

                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(BroBot.fourSpacesIndent + TaskList.printFormattedNumberedTask(taskIndex));

                        BroBot.delimit();
                        return true;
                    } else {
                        BroBot.delimit();
                        BroBot.delimit();
                        return true;
                    }
                }

                case UNMARK: {
                    if (commandTokens.length != 2) {
                        throw MissingNumberException.fromCommandName(commandTokens[0]);
                    }

                    final int taskIndex = Integer.parseInt(commandTokens[1]);
                    if (1 <= taskIndex && taskIndex <= TaskList.size()) {
                        BroBot.delimit();

                        TaskList.unmarkTask(taskIndex);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(BroBot.fourSpacesIndent + TaskList.printFormattedNumberedTask(taskIndex));

                        BroBot.delimit();
                        return true;
                    } else {
                        BroBot.delimit();
                        BroBot.delimit();
                        return true;
                    }
                }

                case DELETE: {
                    if (commandTokens.length != 2) {
                        throw MissingNumberException.fromCommandName(commandTokens[0]);
                    }

                    final int taskIndex = Integer.parseInt(commandTokens[1]);
                    if (1 <= taskIndex && taskIndex <= TaskList.size()) {
                        BroBot.delimit();

                        System.out.println("Noted. I've removed this task:");
                        System.out.println(BroBot.fourSpacesIndent + TaskList.printFormattedNumberedTask(taskIndex));
                        TaskList.remove(taskIndex);
                        System.out.printf("Now you have %d tasks in the list.\n", TaskList.size());

                        BroBot.delimit();
                        return true;
                    } else {
                        BroBot.delimit();
                        BroBot.delimit();
                        return true;
                    }
                }

                case TODO, EVENT, DEADLINE: {
                    BroBot.delimit();
                    TaskList.add(Task.createTask(commandTokens));

                    System.out.println("Got it. I've added this task:");
                    System.out.println(BroBot.fourSpacesIndent + TaskList.printFormattedNumberedTask(TaskList.size()) );
                    System.out.printf("Now you have %d tasks in the list.\n", TaskList.size());

                    BroBot.delimit();
                    return true;
                }

                default: {
                    throw NoSuchCommandNameException.newInstancefromCommandName(String.valueOf(command));
                }
            }

        } catch (final BrobotCommandException brobotCommandException) {
            BroBot.delimit();
            System.out.println(brobotCommandException.getMessage());
            BroBot.delimit();
            return true;
        }
    }

    public static void main (final String[] args) {
        BroBot.greet();
        while (BroBot.processCommand()) {

        }
    }
}
