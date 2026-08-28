package jason;

import jason.exception.InvalidIndexException;
import jason.task.ToDo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests Jason's task coordination and application lifecycle methods. */
class JasonTest {
    private static final Path SAVE_FILE = Path.of("data/jason.txt");
    private byte[] originalSaveFile;
    private boolean saveFileExisted;
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void isolateSaveFile() throws IOException {
        saveFileExisted = Files.exists(SAVE_FILE);
        if (saveFileExisted) originalSaveFile = Files.readAllBytes(SAVE_FILE);
        Files.createDirectories(SAVE_FILE.getParent());
        Files.writeString(SAVE_FILE, "");
    }

    @AfterEach
    void restoreEnvironment() throws IOException {
        System.setIn(originalIn);
        System.setOut(originalOut);
        if (saveFileExisted) {
            Files.write(SAVE_FILE, originalSaveFile);
        } else {
            Files.deleteIfExists(SAVE_FILE);
        }
    }

    @Test
    void addTask_getTaskAndSize_returnsAddedTaskAndCount() {
        Jason jason = new Jason();
        ToDo task = new ToDo("read book");

        jason.addTask(task);

        assertEquals(1, jason.size());
        assertEquals(task, jason.getTask(1));
    }

    @Test
    void markTaskCompleteAndIncomplete_updatesTaskState() throws InvalidIndexException {
        Jason jason = new Jason();
        jason.addTask(new ToDo("read book"));

        jason.markTaskAsComplete(1);
        assertTrue(jason.getTask(1).isCompleted());

        jason.markTaskAsIncomplete(1);
        assertFalse(jason.getTask(1).isCompleted());
    }

    @Test
    void deleteTask_validIndex_removesTask() throws InvalidIndexException {
        Jason jason = new Jason();
        jason.addTask(new ToDo("read book"));

        jason.deleteTask(1);

        assertEquals(0, jason.size());
    }

    @Test
    void taskOperations_invalidIndex_throwException() {
        Jason jason = new Jason();

        assertThrows(InvalidIndexException.class, () -> jason.markTaskAsComplete(1));
        assertThrows(InvalidIndexException.class, () -> jason.markTaskAsIncomplete(1));
        assertThrows(InvalidIndexException.class, () -> jason.deleteTask(1));
    }

    @Test
    void addTask_nullTask_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Jason().addTask(null));
    }

    @Test
    void run_exitCommand_showsWelcomeAndGoodbye() {
        System.setIn(new ByteArrayInputStream("bye\n".getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        new Jason().run();

        String consoleOutput = output.toString();
        assertTrue(consoleOutput.contains("Hello! My name is Jason"));
        assertTrue(consoleOutput.contains("_______"));
        assertTrue(consoleOutput.contains("Goodbye! Hope to see you again."));
    }
}
