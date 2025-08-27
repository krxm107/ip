package brobot.brobotexceptions;

public class BrobotCommandFormatException extends BrobotCheckedException implements Runnable {
    private static final String invalidInputWarning = String.format("Sorry, this input is invalid!");
    private static final String anotherChanceOffering = String.format("Please enter a different input.\nProgram resumed.");

    private static final String getFullMessage (final String mainMessage) {
        return String.join("\n",
                            BrobotCommandFormatException.invalidInputWarning,
                            mainMessage,
                            BrobotCommandFormatException.anotherChanceOffering);
    }

    BrobotCommandFormatException(final String mainMessage) {
        super(BrobotCommandFormatException.getFullMessage(mainMessage));
    }

    public static final BrobotCommandFormatException newInstance() {
        return new BrobotCommandFormatException("");
    }

    public final void run() {
        System.out.println(this.getMessage());
    }
}
