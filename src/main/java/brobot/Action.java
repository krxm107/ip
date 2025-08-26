package brobot;

@FunctionalInterface
public interface Action {
    Runnable getAction();
}
