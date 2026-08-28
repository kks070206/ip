package jason.command;

import jason.storage.Storage;
import jason.task.TaskList;
import jason.ui.Ui;

/** Ends the application without changing the task list. */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // There is no work to do when exiting.
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
