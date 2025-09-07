package brobot.commands;

import brobot.FileIoStatus;
import brobot.TaskList;

public abstract class FileIoCommand extends Command {
    FileIoCommand(String commandName) {
        super(commandName);
    }

    public static final String getSuccessfulFileSaveMessage() {
        final String successfulHardDriveSave = "Your tasks have successfully been saved to the hard drive.";
        final String numberOfTasksLeft = String.format("Now you have %d tasks in the list.",
                                            TaskList.getSingleton().getSize());

        return String.join(System.lineSeparator(), successfulHardDriveSave, numberOfTasksLeft);
    }

    @Override
    public abstract FileIoStatus sendBrobotMessage();
}
