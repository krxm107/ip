package brobot;

// TODO: Finish this class
public final class UI {
    public static final String fourSpacesIndent = String.valueOf(new char[]{' ', ' ', ' ', ' '});

    private static void delimit() {
        System.out.println("____________________________________________________________");
    }

    public static void sendMessage (final Runnable message) {
        UI.delimit();

        message.run();

        UI.delimit();
    }

    public static void performAction (final Action action) {
        UI.sendMessage(action.getAction());
    }
}
