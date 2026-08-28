package jason.command;

import jason.exception.InvalidIndexException;
import jason.storage.Storage;
import jason.task.Task;
import jason.task.TaskList;
import jason.ui.Ui;

/** Deletes one task and persists the updated list. */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws InvalidIndexException {
        if (!tasks.isValidIndex(index)) throw new InvalidIndexException();
        Task deletedTask = tasks.get(index);
        tasks.remove(index);
        storage.save(tasks);
        ui.showDeletedTask(deletedTask, tasks.size());
    }
}
