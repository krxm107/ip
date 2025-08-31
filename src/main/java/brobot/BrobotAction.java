package brobot;

/**
 * This class represents an action that can be performed by Brobot, as per Brobot domain rules.
 */
@FunctionalInterface
public interface BrobotAction {
    /**
     * Perform the BrobotAction, as per Brobot domain rules.
     */
    void performBrobotAction();
}
