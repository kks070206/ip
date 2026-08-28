package jason.task;

/** Represents a simple task without a deadline or event time. */
public class ToDo extends Task {
    /** Creates an incomplete todo task. */
    public ToDo(String description) {
        super(description);
    }

    @Override
    /** Returns the todo type marker followed by the task display. */
    public String toString() {
        return "[T] " + super.toString();
    }
}
