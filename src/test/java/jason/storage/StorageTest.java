package jason.storage;

import jason.task.Deadline;
import jason.task.Event;
import jason.task.Task;
import jason.task.TaskList;
import jason.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests task persistence and error handling in {@link Storage}. */
class StorageTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void load_missingFile_returnsEmptyList() {
        Storage storage = new Storage(temporaryDirectory.resolve("missing.txt").toString());

        assertTrue(storage.load().isEmpty());
    }

    @Test
    void saveAndLoad_tasksRoundTripWithStatusAndDates() {
        Path file = temporaryDirectory.resolve("nested/tasks.txt");
        Storage storage = new Storage(file.toString());
        TaskList original = new TaskList();
        ToDo todo = new ToDo("read book");
        todo.markComplete();
        original.add(todo);
        original.add(new Deadline("submit report", LocalDateTime.of(2019, 10, 15, 18, 0)));
        original.add(new Event("planning", LocalDateTime.of(2020, 1, 2, 14, 0),
                LocalDateTime.of(2020, 1, 2, 16, 30)));

        storage.save(original);
        List<Task> loaded = storage.load();

        assertEquals(3, loaded.size());
        assertTrue(loaded.get(0).isCompleted());
        assertEquals("read book", loaded.get(0).getDescription());
        assertEquals(LocalDateTime.of(2019, 10, 15, 18, 0),
                assertInstanceOf(Deadline.class, loaded.get(1)).getDeadline());
        Event event = assertInstanceOf(Event.class, loaded.get(2));
        assertEquals(LocalDateTime.of(2020, 1, 2, 14, 0), event.getStartDate());
        assertEquals(LocalDateTime.of(2020, 1, 2, 16, 30), event.getEndDate());
    }

    @Test
    void save_emptyTaskList_createsEmptyFile() throws Exception {
        Path file = temporaryDirectory.resolve("empty.txt");
        Storage storage = new Storage(file.toString());

        storage.save(new TaskList());

        assertTrue(Files.exists(file));
        assertEquals("", Files.readString(file));
    }

    @Test
    void load_malformedRecords_skipsInvalidLinesAndKeepsValidTasks() throws Exception {
        Path file = temporaryDirectory.resolve("malformed.txt");
        Files.write(file, List.of(
                "T | 0 | valid task",
                "not a task",
                "D | 1 | invalid date | nope",
                "E | 0 | incomplete event | 2020-01-02T14:00",
                "X | 0 | unknown type",
                "T | 1 | another valid task"));
        Storage storage = new Storage(file.toString());

        List<Task> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertEquals("valid task", loaded.get(0).getDescription());
        assertEquals("another valid task", loaded.get(1).getDescription());
        assertFalse(loaded.get(0).isCompleted());
        assertTrue(loaded.get(1).isCompleted());
    }

    @Test
    void constructor_emptyPath_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Storage(" "));
        assertThrows(IllegalArgumentException.class, () -> new Storage(null));
    }

    @Test
    void save_nullTaskList_throwsException() {
        Storage storage = new Storage(temporaryDirectory.resolve("tasks.txt").toString());

        assertThrows(IllegalArgumentException.class, () -> storage.save(null));
    }
}
