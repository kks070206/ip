package jason.ui;

import jason.exception.InvalidCommandException;
import jason.task.TaskList;
import jason.task.ToDo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests the command-line input and output handled by {@link Ui}. */
class UiTest {
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    private ByteArrayOutputStream output;

    @BeforeEach
    void redirectOutput() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreConsole() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void readCommand_readsOneLineFromStandardInput() {
        System.setIn(new ByteArrayInputStream("list\n".getBytes(StandardCharsets.UTF_8)));
        Ui ui = new Ui();

        assertEquals("list", ui.readCommand());
    }

    @Test
    void showMessages_printExpectedConsoleOutput() {
        Ui ui = new Ui();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("read book");
        tasks.add(task);

        ui.showWelcome();
        ui.showLine();
        ui.showTaskList(tasks);
        ui.showAddedTask(task);
        ui.showMarkedComplete(task);
        ui.showMarkedIncomplete(task);
        ui.showDeletedTask(task, 0);
        ui.showMatchingTasks(List.of(task));
        ui.showMatchingTasks(List.of());
        ui.showError(new InvalidCommandException());
        ui.showGoodbye();

        String consoleOutput = output.toString();
        assertTrue(consoleOutput.contains("Hello! My name is Jason"));
        assertTrue(consoleOutput.contains("_______"));
        assertTrue(consoleOutput.contains("1. [T] [ ] read book"));
        assertTrue(consoleOutput.contains("Added: [T] [ ] read book"));
        assertTrue(consoleOutput.contains("Nice! I have marked this task as done:"));
        assertTrue(consoleOutput.contains("OK, I've marked this task as not done yet:"));
        assertTrue(consoleOutput.contains("You have 0 tasks left in your list"));
        assertTrue(consoleOutput.contains("Here are the matching tasks in your list:"));
        assertTrue(consoleOutput.contains("No matching tasks found."));
        assertTrue(consoleOutput.contains("Invalid command."));
        assertTrue(consoleOutput.contains("Goodbye! Hope to see you again."));
    }
}
