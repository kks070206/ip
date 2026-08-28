package jason.ui;

import java.util.Scanner;
import java.util.List;
import jason.Jason;
import jason.task.Task;
import jason.task.TaskList;

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

    /** Reads the next command entered by the user.
     *
     * @return next command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Prints the chatbot's goodbye message. */
    public void showGoodbye() {
        System.out.println(Jason.END_MESSAGE);
    }

    /** Prints the divider between command-line interactions. */
    public void showLine() {
        System.out.println("_______");
    }

    /** Prints an error message.
     *
     * @param exception exception whose message should be shown.
     */
    public void showError(Exception exception) {
        System.out.println(exception);
    }

    /** Displays the current task list.
     *
     * @param taskList task list to display.
     */
    public void showTaskList(TaskList taskList) {
        System.out.println(taskList);
    }

    /** Displays tasks matching a search keyword.
     *
     * @param matchingTasks tasks that matched the user's search.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
            return;
        }

        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, matchingTasks.get(i));
        }
    }

    /** Displays a newly added task.
     *
     * @param task task that was added.
     */
    public void showAddedTask(Task task) {
        System.out.println("Added: " + task);
    }

    /** Displays a completed task confirmation.
     *
     * @param task task that was marked complete.
     */
    public void showMarkedComplete(Task task) {
        System.out.println("Nice! I have marked this task as done:");
        System.out.println(task);
    }

    /** Displays an incomplete task confirmation.
     *
     * @param task task that was marked incomplete.
     */
    public void showMarkedIncomplete(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /** Displays a task deletion confirmation and the remaining task count.
     *
     * @param task task that was deleted.
     * @param remainingTasks number of tasks left after deletion.
     */
    public void showDeletedTask(Task task, int remainingTasks) {
        System.out.println("Alright. I will remove this task:");
        System.out.println(task);
        System.out.printf("You have %d tasks left in your list%n", remainingTasks);
    }
}
