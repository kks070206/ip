import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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

    /** Writes the current tasks to disk, without stopping the chatbot if saving fails. */
    private void saveTasks() {
        try {
            Files.createDirectories(SAVE_FILE.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(SAVE_FILE)) {
                for (int i = 1; i <= taskList.size(); i++) {
                    writer.write(toSaveFormat(taskList.get(i)));
                    writer.newLine();
                }
            }
        } catch (IOException | SecurityException e) {
            reportPersistenceError("save", e);
        }
    }

    /** Converts a task into one line of the save-file format. */
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

    /** Loads tasks at startup; a missing file is a normal first-run condition. */
    private void loadTasks() {
        try {
            for (String line : Files.readAllLines(SAVE_FILE)) {
                Task task = fromSaveFormat(line);
                if (task != null) taskList.add(task);
            }
        } catch (NoSuchFileException e) {
            // There is no save file to load on the first run.
        } catch (IOException | SecurityException e) {
            reportPersistenceError("load", e);
        }
    }

    /** Parses one saved task, returning null when the record is invalid. */
    private Task fromSaveFormat(String line) {
        if (line == null || line.isBlank()) return null;

        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) return null;

        String type = fields[0].trim();
        String status = fields[1].trim();
        String description = fields[2].trim();
        if (description.isEmpty() || !(status.equals("0") || status.equals("1"))) return null;

        Task task;
        switch (type) {
            case "T" -> {
                if (fields.length != 3) return null;
                task = new ToDo(description);
            }
            case "D" -> {
                if (fields.length != 4 || fields[3].trim().isEmpty()) return null;
                task = new Deadline(description, fields[3].trim());
            }
            case "E" -> {
                if (fields.length == 5) {
                    task = new Event(description, fields[3].trim(), fields[4].trim());
                } else if (fields.length == 4) {
                    String[] times = fields[3].trim().split("-", 2);
                    if (times.length != 2) return null;
                    task = new Event(description, times[0].trim(), times[1].trim());
                } else {
                    return null;
                }
                if (task instanceof Event event
                        && (event.getStartTime().isEmpty() || event.getEndTime().isEmpty())) return null;
            }
            default -> {
                return null;
            }
        }

        if (status.equals("1")) task.markComplete();
        return task;
    }

    /** Reports a persistence error while allowing the chatbot to continue running. */
    private void reportPersistenceError(String operation, Exception exception) {
        System.err.printf("Warning: unable to %s tasks at %s (%s)%n",
                operation, SAVE_FILE, exception.getMessage());
    }
}
