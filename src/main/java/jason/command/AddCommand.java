package jason.command;

import jason.storage.Storage;
import jason.task.Task;
import jason.task.TaskList;
import jason.ui.Ui;

/** Adds a task to the task list and persists the updated list. */
public class AddCommand extends Command {
    private final Task task;

    /** Creates an add command for a task. */
    public AddCommand(Task task) {
        this.task = task;
    }

    /** Adds the task, saves the list, and reports the addition. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        storage.save(tasks);
        ui.showAddedTask(task);
    }
}
