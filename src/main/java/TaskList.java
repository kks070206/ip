import java.util.List;
import java.util.ArrayList;

public class TaskList {
    private List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public void add(Task t) {
        this.taskList.add(t);
    }

    public Task get(int i) {
        return this.taskList.get(i - 1);
    }

    public int size() {
        return this.taskList.size();
    }

    public boolean isValidIndex(int i) {
        return i <= this.size() && i > 0;
    }

    @Override
    public String toString() {
        if (taskList.isEmpty()) return "List is empty. Please add something!";
        String res = "";
        for (int i = 1; i <= taskList.size(); i++) {
            res += String.format("%d. %s\n", i, this.taskList.get(i - 1));
        }
        res += String.format("You have %d tasks.", this.taskList.size());
        return res;
    }

}
