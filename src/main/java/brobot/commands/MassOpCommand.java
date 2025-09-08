package brobot.commands;

import brobot.BrobotMessenger;
import brobot.FileIoStatus;

/**
 * Marker interface for BCD-Extension (C-MassOps).
 * This marks a command that allows for mass ops.
 */
@FunctionalInterface
public interface MassOpCommand extends BrobotMessenger {
    @Override
    FileIoStatus sendBrobotMessage();
}
