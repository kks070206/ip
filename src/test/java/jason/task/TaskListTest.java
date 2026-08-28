package jason.task;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests the public behavior of {@link TaskList}. */
class TaskListTest {
    @Test
    void constructor_withTasks_initializesListWithThoseTasks() {
        Task first = new ToDo("first");
        Task second = new ToDo("second");

        TaskList tasks = new TaskList(List.of(first, second));

        assertEquals(2, tasks.size());
        assertSame(first, tasks.get(1));
        assertSame(second, tasks.get(2));
    }

    @Test
    void add_newTask_increasesSizeAndPreservesOrder() {
        TaskList tasks = new TaskList();
        Task first = new ToDo("first");
        Task second = new ToDo("second");

        tasks.add(first);
        tasks.add(second);

        assertEquals(2, tasks.size());
        assertSame(first, tasks.get(1));
        assertSame(second, tasks.get(2));
    }

    @Test
    void get_validOneBasedIndex_returnsTaskAtPosition() {
        TaskList tasks = new TaskList();
        Task first = new ToDo("first");
        Task second = new ToDo("second");
        tasks.add(first);
        tasks.add(second);

        assertSame(first, tasks.get(1));
        assertSame(second, tasks.get(2));
    }

    @Test
    void get_zeroOrNegativeIndex_throwsException() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("task"));

        assertThrows(IndexOutOfBoundsException.class, () -> tasks.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.get(-1));
    }

    @Test
    void get_indexBeyondListSize_throwsException() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("task"));

        assertThrows(IndexOutOfBoundsException.class, () -> tasks.get(2));
    }

    @Test
    void remove_validIndex_decreasesSizeAndRemovesTask() {
        TaskList tasks = new TaskList();
        Task first = new ToDo("first");
        Task second = new ToDo("second");
        tasks.add(first);
        tasks.add(second);

        tasks.remove(1);

        assertEquals(1, tasks.size());
        assertSame(second, tasks.get(1));
    }

    @Test
    void remove_invalidIndex_throwsException() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("task"));

        assertThrows(IndexOutOfBoundsException.class, () -> tasks.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.remove(2));
    }

    @Test
    void toString_emptyList_returnsEmptyMessage() {
        TaskList tasks = new TaskList();

        assertEquals("List is empty. Please add something!", tasks.toString());
    }

    @Test
    void toString_populatedList_returnsNumberedTasksAndCount() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("first"));
        tasks.add(new ToDo("second"));

        assertEquals("Here are the tasks on your list: \n"
                + "1. [T] [ ] first\n"
                + "2. [T] [ ] second\n"
                + "You have 2 tasks.", tasks.toString());
    }

    @Test
    void isValidIndex_emptyList_returnsFalse() {
        TaskList tasks = new TaskList();

        assertFalse(tasks.isValidIndex(1));
    }

    @Test
    void isValidIndex_validPositions_returnsTrue() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("first"));
        tasks.add(new ToDo("second"));
        tasks.add(new ToDo("third"));

        assertTrue(tasks.isValidIndex(1));
        assertTrue(tasks.isValidIndex(2));
        assertTrue(tasks.isValidIndex(3));
    }

    @Test
    void isValidIndex_zeroOrNegativeIndex_returnsFalse() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("task"));

        assertFalse(tasks.isValidIndex(0));
        assertFalse(tasks.isValidIndex(-1));
    }

    @Test
    void isValidIndex_indexBeyondListSize_returnsFalse() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("task"));

        assertFalse(tasks.isValidIndex(2));
        assertFalse(tasks.isValidIndex(Integer.MAX_VALUE));
    }
}
