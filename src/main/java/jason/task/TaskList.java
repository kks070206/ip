package jason.task;

import java.util.ArrayList;
import java.util.List;

/** Stores tasks and provides one-based list operations for the application. */
public class TaskList {
    private List<Task> taskList;

    /** Creates an empty task list. */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /** Creates a task list containing the supplied tasks. */
    /** Creates a task list containing a copy of the supplied tasks. */
    public TaskList(List<Task> tasks) {
        this.taskList = new ArrayList<>(tasks);
    }

    /** Appends a task to the list. */
    public void add(Task t) {
        this.taskList.add(t);
    }

    /** Returns the task at a one-based index. */
    public Task get(int i) {
        return this.taskList.get(i - 1);
    }

    /** Returns the number of tasks in the list. */
    public int size() {
        return this.taskList.size();
    }

    /** Removes the task at a one-based index. */
    public void remove(int i) {
        this.taskList.remove(get(i));
    }

    /** Returns whether a one-based index identifies a task in this list. */
    public boolean isValidIndex(int i) {
        return i <= this.size() && i > 0;
    }

    /** Returns a numbered, human-readable representation of the task list. */
    @Override
    public String toString() {
        if (taskList.isEmpty()) {
            return "List is empty. Please add something!";
        }

        String res = "Here are the tasks on your list: \n";

        for (int i = 1; i <= taskList.size(); i++) {
            res += String.format("%d. %s\n", i, this.taskList.get(i - 1));
        }

        res += String.format("You have %d tasks.", this.taskList.size());

        return res;
    }

}
