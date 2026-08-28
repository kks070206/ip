public abstract class Task {
    private String description;
    private boolean isCompleted;

    public Task(String description) {
        this.description = description;
        isCompleted = false;
    }

    public void markComplete() {
        this.isCompleted = true;
    }

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
    public String toString()  {
        if (this.isCompleted) {
            return String.format("[X] %s", this.description);
        }
        return String.format("[ ] %s", this.description);
    }
}
