public class Task {
    String task;
    boolean isCompleted;

    public Task(String task) {
        this.task = task;
        isCompleted = false;
    }

    @Override
    public String toString()  {
        if (this.isCompleted) {
            return String.format("[X] %s", this.task);
        }
        return String.format("[ ] %s", task);
    }
}
