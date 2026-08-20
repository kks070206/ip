public class Task {
    String description;
    boolean isCompleted;

    public Task(String description) {
        this.description = description;
        isCompleted = false;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public void markIncomplete() {
        this.isCompleted = false;
    }

    @Override
    public String toString()  {
        if (this.isCompleted) {
            return String.format("[X] %s", this.description);
        }
        return String.format("[ ] %s", this.description);
    }
}