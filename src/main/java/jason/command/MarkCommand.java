package jason.command;

import jason.exception.InvalidIndexException;
import jason.storage.Storage;
import jason.task.TaskList;
import jason.ui.Ui;

/** Marks one task as complete and persists the updated list. */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws InvalidIndexException {
        if (!tasks.isValidIndex(index)) throw new InvalidIndexException();
        tasks.get(index).markComplete();
        storage.save(tasks);
        ui.showMarkedComplete(tasks.get(index));
    }
}
