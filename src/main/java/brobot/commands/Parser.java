package brobot.commands;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.brobotexceptions.EmptyCommandException;
import brobot.brobotexceptions.NoSuchCommandNameException;

final class Parser {
    private Parser() {

    }

    public static Command parseCommand (final String commandInput) throws BrobotCommandFormatException {
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

    private static Command parseCommand (final String commandName, final String... commandTokens) throws BrobotCommandFormatException {
        final String cleanedCommandName = commandName.strip().toLowerCase();
        return switch (cleanedCommandName) {
            case "todo", "event", "deadline" -> AddTaskCommand.makeCommand(cleanedCommandName, commandTokens);

            case "bye" -> ByeCommand.makeCommand(cleanedCommandName, commandTokens);

            case "list" -> ListCommand.makeCommand(cleanedCommandName, commandTokens);

            case "mark" -> MarkCommand.makeCommand(cleanedCommandName, commandTokens);

            case "unmark" -> UnmarkCommand.makeCommand(commandName, commandTokens);

            case "delete" -> DeleteCommand.makeCommand(commandName, commandTokens);

            default -> throw NoSuchCommandNameException.newInstancefromCommandName(commandName);
        };
    }
}
