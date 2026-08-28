package jason.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import jason.task.Deadline;
import jason.task.Event;
import jason.task.Task;
import jason.task.TaskList;
import jason.task.ToDo;

/** Handles loading tasks from and saving tasks to a file. */
public class Storage {
    private final Path filePath;

    /** Creates storage backed by the supplied relative or absolute path.
     *
     * @param filePath relative or absolute path of the save file.
     * @throws IllegalArgumentException if the path is null or blank.
     */
    public Storage(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("The storage file path cannot be empty.");
        }
        this.filePath = Path.of(filePath);
    }

    /** Loads all valid tasks, returning an empty list when the file does not exist.
     *
     * @return valid tasks read from the save file.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(filePath)) {
                Task task = fromSaveFormat(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (NoSuchFileException e) {
            // No file is expected on the first run.
        } catch (IOException | SecurityException e) {
            reportError("load", e);
        }
        return tasks;
    }

    /** Saves the current task list, creating its parent directory when needed.
     *
     * @param tasks task list to save.
     * @throws IllegalArgumentException if the task list is null.
     */
    public void save(TaskList tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("The task list cannot be null.");
        }
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (int i = 1; i <= tasks.size(); i++) {
                    writer.write(toSaveFormat(tasks.get(i)));
                    writer.newLine();
                }
            }
        } catch (IOException | SecurityException e) {
            reportError("save", e);
        }
    }

    /** Converts a task into the line format used by the save file. */
    private String toSaveFormat(Task task) {
        String status = task.isCompleted() ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return String.format("D | %s | %s | %s", status,
                    task.getDescription(), deadline.getDeadline());
        }
        if (task instanceof Event event) {
            return String.format("E | %s | %s | %s | %s", status,
                    task.getDescription(), event.getStartDate(), event.getEndDate());
        }
        return String.format("T | %s | %s", status, task.getDescription());
    }

    /** Parses one saved line, returning null when the record is malformed. */
    private Task fromSaveFormat(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] fields = line.split("\\s*\\|\\s*", -1);
        if (fields.length < 3) {
            return null;
        }

        String type = fields[0].trim();
        String status = fields[1].trim();
        String description = fields[2].trim();
        if (description.isEmpty() || !(status.equals("0") || status.equals("1"))) {
            return null;
        }

        Task task;
        switch (type) {
            case "T" -> {
                if (fields.length != 3) {
                    return null;
                }
                task = new ToDo(description);
            }
            case "D" -> {
                if (fields.length != 4 || fields[3].trim().isEmpty()) {
                    return null;
                }
                try {
                    task = new Deadline(description, fields[3].trim());
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
            case "E" -> {
                if (fields.length != 5 || fields[3].trim().isEmpty()
                        || fields[4].trim().isEmpty()) {
                    return null;
                }
                try {
                    task = new Event(description, parseDateTime(fields[3].trim()),
                            parseDateTime(fields[4].trim()));
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
            default -> {
                return null;
            }
        }
        if (status.equals("1")) {
            task.markComplete();
        }
        return task;
    }

    /** Parses a saved date-time, accepting older date-only records as midnight. */
    private LocalDateTime parseDateTime(String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(value).atStartOfDay();
        }
    }

    /** Reports an I/O or permission problem without terminating the application. */
    private void reportError(String operation, Exception exception) {
        System.err.printf("Warning: unable to %s tasks at %s (%s)%n",
                operation, filePath, exception.getMessage());
    }
}
