import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Jason {
    public static final String START_MESSAGE = "Hello! My name is Jason, inspired by JSON files used by software engineers.";
    public static final String HELP_MESSAGE = "How may I help you today?";
    public static final String END_MESSAGE = "Goodbye! Hope to see you again.";
    private static final Path SAVE_FILE = Path.of("./data/jason.txt");
    public TaskList taskList;

    public Jason() {
        this.taskList = new TaskList();
        loadTasks();
    }

    public void addTask(Task t) {
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
        try {
            Files.createDirectories(SAVE_FILE.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(SAVE_FILE)) {
                for (int i = 1; i <= taskList.size(); i++) {
                    writer.write(toSaveFormat(taskList.get(i)));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save tasks to " + SAVE_FILE, e);
        }
    }

    /**
     * Converts a task to the simple line format used by the save file.
     */
    private String toSaveFormat(Task task) {
        String status = task.isCompleted() ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return String.format("D | %s | %s | %s", status,
                    task.getDescription(), deadline.getDeadline());
        }
        if (task instanceof Event event) {
            return String.format("E | %s | %s | %s-%s", status,
                    task.getDescription(), event.getStartTime(), event.getEndTime());
        }
        return String.format("T | %s | %s", status, task.getDescription());
    }

    /**
     * Loads previously saved tasks when the chatbot starts.
     * Missing files are expected on the first run, while malformed lines are ignored.
     */
    private void loadTasks() {
        if (!Files.exists(SAVE_FILE)) return;

        try {
            for (String line : Files.readAllLines(SAVE_FILE)) {
                Task task = fromSaveFormat(line);
                if (task != null) this.taskList.add(task);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load tasks from " + SAVE_FILE, e);
        }
    }

    /**
     * Converts one saved line back into a task, or returns null for an invalid line.
     */
    private Task fromSaveFormat(String line) {
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) return null;

        String type = fields[0].trim();
        String status = fields[1].trim();
        String description = fields[2].trim();
        if (description.isEmpty() || !(status.equals("0") || status.equals("1"))) return null;

        Task task;
        switch (type) {
            case "T" -> task = new ToDo(description);
            case "D" -> {
                if (fields.length < 4 || fields[3].trim().isEmpty()) return null;
                task = new Deadline(description, fields[3].trim());
            }
            case "E" -> {
                if (fields.length < 4) return null;
                String[] times = fields[3].trim().split("-", 2);
                if (times.length < 2 || times[0].isEmpty() || times[1].isEmpty()) return null;
                task = new Event(description, times[0].trim(), times[1].trim());
            }
            default -> {
                return null;
            }
        }

        if (status.equals("1")) task.markComplete();
        return task;
    }
}
