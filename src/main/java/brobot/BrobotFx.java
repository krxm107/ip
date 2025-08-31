package brobot;

/**
 * The Brobot class for JavaFX.
 */
public final class BrobotFx {
    public static void main(String[] args) {
        System.out.println("Hello!");
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }
}
