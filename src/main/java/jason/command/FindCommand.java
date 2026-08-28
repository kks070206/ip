package jason.command;

import jason.storage.Storage;
import jason.task.TaskList;
import jason.ui.Ui;

/** Finds and displays tasks whose descriptions contain a keyword. */
public class FindCommand extends Command {
    private final String keyword;

    /** Creates a find command for the supplied keyword. */
    public FindCommand(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("A search keyword is required.");
        }
        this.keyword = keyword.trim();
    }

    /** Searches the task list and displays the matching tasks. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMatchingTasks(tasks.find(keyword));
    }
}
