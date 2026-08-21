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
    @Override
    public String toString() {
        String res = "";
        for (int i = 1; i <= taskList.size(); i++) {
            res += String.format("%d. %s\n", i, this.taskList.get(i - 1));
        }
        return res;
    }

}
