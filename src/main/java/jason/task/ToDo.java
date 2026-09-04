package jason.task;

/**
 * Represents a simple task without a deadline or event time.
 */
public class ToDo extends Task {
    /**
     * Creates an incomplete todo task.
     *
     * @param description text describing the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the todo type marker followed by the task display.
     *
     * @return formatted todo description.
     */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
