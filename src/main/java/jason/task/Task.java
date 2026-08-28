package jason.task;

/** Represents a task with a description and completion state. */
public abstract class Task {
    private String description;
    private boolean isCompleted;

    /** Creates an incomplete task with the supplied description. */
    public Task(String description) {
        this.description = description;
        isCompleted = false;
    }

    /** Marks this task as complete. */
    public void markComplete() {
        this.isCompleted = true;
    }

    /** Marks this task as incomplete. */
    public void markIncomplete() {
        this.isCompleted = false;
    }

    /**
     * Returns the task description for persistence.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether this task has been completed.
     */
    public boolean isCompleted() {
        return this.isCompleted;
    }

    @Override
    /** Returns the completion marker and description for display. */
    public String toString()  {
        if (this.isCompleted) {
            return String.format("[X] %s", this.description);
        }
        return String.format("[ ] %s", this.description);
    }
}
