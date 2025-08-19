package brobot.brobotexceptions;

public abstract class BrobotCommandException extends BrobotCheckedException {
    private static final String invalidInputWarning = String.format("Sorry, this input is invalid!");
    private static final String anotherChanceOffering = String.format("Please enter a different input.\nProgram resumed.");

    private static final String getFullMessage (final String mainMessage) {
        return String.join("\n",
                            BrobotCommandException.invalidInputWarning,
                            mainMessage,
                            BrobotCommandException.anotherChanceOffering);
    }

    protected BrobotCommandException (final String mainMessage) {
        super(BrobotCommandException.getFullMessage(mainMessage));
    }
}
