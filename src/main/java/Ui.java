import java.util.Scanner;

/**
 * Handles all interaction between Jason and the command-line user.
 */
public class Ui {
    private final Scanner scanner;

    /** Creates a UI connected to standard input and output. */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Prints the chatbot's welcome messages. */
    public void showWelcome() {
        System.out.println(Jason.START_MESSAGE);
        System.out.println(Jason.HELP_MESSAGE);
    }

    /** Reads the next command entered by the user. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Prints the chatbot's goodbye message. */
    public void showGoodbye() {
        System.out.println(Jason.END_MESSAGE);
    }

    /** Prints an error message. */
    public void showError(Exception exception) {
        System.out.println(exception);
    }

    /** Displays the current task list. */
    public void showTaskList(TaskList taskList) {
        System.out.println(taskList);
    }

    /** Displays a newly added task. */
    public void showAddedTask(Task task) {
        System.out.println("Added: " + task);
    }

    /** Displays a completed task confirmation. */
    public void showMarkedComplete(Task task) {
        System.out.println("Nice! I have marked this task as done:");
        System.out.println(task);
    }

    /** Displays an incomplete task confirmation. */
    public void showMarkedIncomplete(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /** Displays a task deletion confirmation and the remaining task count. */
    public void showDeletedTask(Task task, int remainingTasks) {
        System.out.println("Alright. I will remove this task:");
        System.out.println(task);
        System.out.printf("You have %d tasks left in your list%n", remainingTasks);
    }
}
