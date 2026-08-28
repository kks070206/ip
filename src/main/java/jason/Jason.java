package jason;

import jason.command.Command;
import jason.exception.InvalidIndexException;
import jason.storage.Storage;
import jason.task.Task;
import jason.task.TaskList;
import jason.ui.Ui;

/** Coordinates the user interface, command execution, task list, and storage. */
public class Jason {
    public static final String START_MESSAGE = "Hello! My name is Jason, inspired by JSON files used by software engineers.";
    public static final String HELP_MESSAGE = "How may I help you today?";
    public static final String END_MESSAGE = "Goodbye! Hope to see you again.";
    private static final String SAVE_FILE = "./data/jason.txt";
    private final Storage storage;
    private final Ui ui;
    private final Parser parser;
    public TaskList taskList;

    /** Creates Jason with the default command-line UI. */
    public Jason() {
        this(new Ui());
    }

    /** Creates Jason with the UI used by its application loop. */
    public Jason(Ui ui) {
        if (ui == null) throw new IllegalArgumentException("The UI cannot be null.");
        this.storage = new Storage(SAVE_FILE);
        this.taskList = new TaskList(storage.load());
        this.ui = ui;
        this.parser = new Parser();
    }

    /** Adds a task and persists the updated task list. */
    public void addTask(Task t) {
        if (t == null) throw new IllegalArgumentException("A task cannot be null.");
        this.taskList.add(t);
        saveTasks();
    }

    /** Returns the task at a one-based list index. */
    public Task getTask(int i) {
        return this.taskList.get(i);
    }

    /** Returns the number of tasks currently stored. */
    public int size() {
        return this.taskList.size();
    }

    /** Marks the task at a one-based index complete and persists the change. */
    public void markTaskAsComplete(int i) throws InvalidIndexException {
        if (!taskList.isValidIndex(i)) throw new InvalidIndexException();
        this.getTask(i).markComplete();
        saveTasks();
    }

    /** Marks the task at a one-based index incomplete and persists the change. */
    public void markTaskAsIncomplete(int i) throws InvalidIndexException {
        if (!taskList.isValidIndex(i)) throw new InvalidIndexException();
        this.getTask(i).markIncomplete();
        saveTasks();
    }

    /** Deletes the task at a one-based index and persists the change. */
    public void deleteTask(int i) throws InvalidIndexException{
        if (!taskList.isValidIndex(i)) throw new InvalidIndexException();
        this.taskList.remove(i);
        saveTasks();
    }

    /**
     * Writes the current task list to disk after a successful list mutation.
     * The parent directory is created automatically the first time the file is saved.
     */
    private void saveTasks() {
        storage.save(taskList);
    }

    /** Runs the command-line application until the user enters {@code bye}. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command command = parser.parse(fullCommand);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();
            } catch (Exception e) {
                ui.showError(e);
            } finally {
                ui.showLine();
            }
        }

        ui.showGoodbye();
    }
}
