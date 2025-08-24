package brobot.brobotexceptions;

public abstract class BrobotCheckedException extends Exception implements Runnable {
    protected BrobotCheckedException (final String fullMessage) {
        super(fullMessage);
    }

    @Override
    public final void run() {
        System.out.println(this.getMessage());
    }
}
