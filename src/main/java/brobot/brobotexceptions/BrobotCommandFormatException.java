package brobot.brobotexceptions;

import brobot.Action;

public class BrobotCommandFormatException extends BrobotCheckedException implements Action {
    private static final String INVALID_INPUT_WARNING = String.format("Sorry, this input is invalid!");
    private static final String ANOTHER_CHANCE_OFFERING = String.format("Please enter a different input.\nProgram resumed.");

    private static final String getFullMessage (final String mainMessage) {
        return String.join("\n",
                            BrobotCommandFormatException.INVALID_INPUT_WARNING,
                            mainMessage,
                            BrobotCommandFormatException.ANOTHER_CHANCE_OFFERING);
    }

    BrobotCommandFormatException(final String mainMessage) {
        super(BrobotCommandFormatException.getFullMessage(mainMessage));
    }

    public static final BrobotCommandFormatException newInstance() {
        return new BrobotCommandFormatException("");
    }

    public final Runnable getAction() {
        return () -> System.out.println(this.getMessage());
    }
}
