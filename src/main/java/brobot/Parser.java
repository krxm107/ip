package brobot;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.brobotexceptions.EmptyCommandException;
import brobot.brobotexceptions.NoSuchCommandNameException;
import brobot.commands.AddTaskCommand;
import brobot.commands.ByeCommand;
import brobot.commands.Command;
import brobot.commands.DeleteCommand;
import brobot.commands.FindCommand;
import brobot.commands.ListCommand;
import brobot.commands.MarkCommand;
import brobot.commands.UnmarkCommand;


public final class Parser {
    private Parser() {

    }

    public static Command parseCommand(final String commandInput) throws BrobotCommandFormatException {
        if (commandInput == null || commandInput.isEmpty()) {
            throw new EmptyCommandException();
        }

        final String[] parts = commandInput.strip().split("\\s+", -1);

        final String[] tokens = new String[parts.length - 1];
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = parts[i + 1];
        }

        return Parser.parseCommand(parts[0], tokens);
    }

    private static Command parseCommand(final String commandName, final String... commandTokens) throws BrobotCommandFormatException {
        final String cleanedCommandName = commandName.strip().toLowerCase();
        try {
            return switch (cleanedCommandName) {
                case "todo", "event", "deadline" -> AddTaskCommand.makeCommand(cleanedCommandName, commandTokens);

                case "bye" -> ByeCommand.makeCommand(cleanedCommandName, commandTokens);

                case "list" -> ListCommand.makeCommand(cleanedCommandName, commandTokens);

                case "mark" -> MarkCommand.makeCommand(cleanedCommandName, commandTokens);

                case "unmark" -> UnmarkCommand.makeCommand(commandName, commandTokens);

                case "delete" -> DeleteCommand.makeCommand(commandName, commandTokens);

                case "find" -> FindCommand.makeCommand(commandTokens);

                default -> throw NoSuchCommandNameException.newInstancefromCommandName(commandName);
            };
        } catch (final RuntimeException e) {
            throw BrobotCommandFormatException.newInstance();
        }
    }
}
