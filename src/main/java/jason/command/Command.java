package jason.command;

import jason.exception.InvalidDeadlineException;
import jason.exception.InvalidEventException;
import jason.exception.InvalidIndexException;
import jason.exception.InvalidToDoException;
import jason.storage.Storage;
import jason.task.TaskList;
import jason.ui.Ui;

/** A parsed user command that can be executed by the application. */
public abstract class Command {
    /** Executes this command against the application state. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws InvalidToDoException, InvalidDeadlineException, InvalidEventException,
            InvalidIndexException;

    /** Returns whether this command ends the application. */
    public boolean isExit() {
        return false;
    }
}
