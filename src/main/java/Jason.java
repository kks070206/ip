public class Jason {
    public static final String START_MESSAGE = "Hello! My name is Jason, inspired by JSON files used by software engineers.";
    public static final String HELP_MESSAGE = "How may I help you today?";
    public static final String END_MESSAGE = "Goodbye! Hope to see you again.";
    public TaskList taskList;

    public Jason() {
        this.taskList = new TaskList();
    }

    public void addTask(Task t) {
        this.taskList.add(t);
    }

    public Task getTask(int i) {
        return this.taskList.get(i);
    }

    public void markTaskAsComplete(int i) throws InvalidIndexException {
        if (!taskList.isValidIndex(i)) throw new InvalidIndexException();
        this.getTask(i).markComplete();
    }

    public void markTaskAsIncomplete(int i) throws InvalidIndexException {
        if (!taskList.isValidIndex(i)) throw new InvalidIndexException();
        this.getTask(i).markIncomplete();
    }
}
