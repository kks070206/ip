/** Marks one task as incomplete and persists the updated list. */
public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws InvalidIndexException {
        if (!tasks.isValidIndex(index)) throw new InvalidIndexException();
        tasks.get(index).markIncomplete();
        storage.save(tasks);
        ui.showMarkedIncomplete(tasks.get(index));
    }
}
