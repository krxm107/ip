package brobot;

import org.junit.jupiter.api.BeforeEach;

/**
 * This interface represents a stateful Unit Tester,
 * where each test requires a clean, clear state at the beginning
 * to prevent unwanted side effects and dependencies.
 */
public interface StatefulTester {
    /**
     * Runs before each test to clear the state and avoid said dependencies.
     * By default, we clear the TaskList singleton so that it's empty and well-behaved.
     */
    @BeforeEach
    default void clearState() {
        StatefulTester.clearTaskList();
    }

    /**
     * Public static helper method to clear the tasklist singleton to help reset the state.
     * DO NOT HIDE OR OVERRIDE.
     */
    static void clearTaskList() {
        while (!TaskList.getSingleton().isEmpty()) {
            TaskList.getSingleton().remove(TaskList.getSingleton().size());
        }
    }
}
