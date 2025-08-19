package brobot;

import brobot.brobotexceptions.NoSuchCommandNameException;

public enum Command {
    BYE, LIST,
    MARK, UNMARK, DELETE,
    TODO, EVENT, DEADLINE;

    public static final Command fromNameIgnoreCase (final String name) throws NoSuchCommandNameException {
        if (name == null) {
            throw NoSuchCommandNameException.newInstancefromCommandName(name);
        }

        try {
            return Command.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException illegalName) {
            throw NoSuchCommandNameException.newInstancefromCommandName(name);
        }
    }

    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
