package brobot.brobotexceptions;

import brobot.Action;

public abstract class BrobotCommandFormatException extends BrobotCheckedException implements Action {
    private static final String invalidInputWarning = String.format("Sorry, this input is invalid!");
    private static final String anotherChanceOffering = String.format("Please enter a different input.\nProgram resumed.");

    private static final String getFullMessage (final String mainMessage) {
        return String.join("\n",
                            BrobotCommandFormatException.invalidInputWarning,
                            mainMessage,
                            BrobotCommandFormatException.anotherChanceOffering);
    }

    protected BrobotCommandFormatException(final String mainMessage) {
        super(BrobotCommandFormatException.getFullMessage(mainMessage));
    }

    public final Runnable getAction() {
        return () -> System.out.println(this.getMessage());
    }
}
