package jason.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests task state, description, and display behavior. */
class TaskTest {
    @Test
    void task_newTask_isIncompleteAndDisplaysDescription() {
        Task task = new Task("read book") { };

        assertEquals("read book", task.getDescription());
        assertFalse(task.isCompleted());
        assertEquals("[ ] read book", task.toString());
    }

    @Test
    void markComplete_thenMarkIncomplete_updatesCompletionState() {
        Task task = new Task("read book") { };

        task.markComplete();
        assertTrue(task.isCompleted());
        assertEquals("[X] read book", task.toString());

        task.markIncomplete();
        assertFalse(task.isCompleted());
        assertEquals("[ ] read book", task.toString());
    }

    @Test
    void todo_toString_includesTodoType() {
        assertEquals("[T] [ ] read book", new ToDo("read book").toString());
    }
}
