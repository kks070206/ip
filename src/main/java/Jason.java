public class Jason {
    public static final String START_MESSAGE = "Hello! My name is Jason, inspired by JSON files used by software engineers.";
    public static final String HELP_MESSAGE = "How may I help you today?";
    public static final String END_MESSAGE = "Goodbye! Hope to see you again.";
    private static final String SAVE_FILE = "./data/jason.txt";
    private final Storage storage;
    public TaskList taskList;

    public Jason() {
        this.storage = new Storage(SAVE_FILE);
        this.taskList = new TaskList(storage.load());
    }

    public void addTask(Task t) {
        if (t == null) throw new IllegalArgumentException("A task cannot be null.");
        this.taskList.add(t);
        saveTasks();
    }

    public Task getTask(int i) {
        return this.taskList.get(i);
    }

    public int size() {
        return this.taskList.size();
    }

    public void markTaskAsComplete(int i) throws InvalidIndexException {
        if (!taskList.isValidIndex(i)) throw new InvalidIndexException();
        this.getTask(i).markComplete();
        saveTasks();
    }

    public void markTaskAsIncomplete(int i) throws InvalidIndexException {
        if (!taskList.isValidIndex(i)) throw new InvalidIndexException();
        this.getTask(i).markIncomplete();
        saveTasks();
    }

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
}
