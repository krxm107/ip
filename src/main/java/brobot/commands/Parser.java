package brobot.commands;

import brobot.brobotexceptions.BrobotCommandFormatException;
import brobot.brobotexceptions.NoSuchCommandNameException;

final class Parser {
    private Parser() {

    }

    public static Command parseCommand (final String commandName, final String... commandTokens) throws BrobotCommandFormatException {
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
