package brobot.commands;


// TODO: Finish UnmarkCommand
final class UnmarkCommand extends Command {

    private final int unmarkIndex;
    private UnmarkCommand (final int unmarkIndex) {
        super("unmark");
        this.unmarkIndex = unmarkIndex;
    }


}
