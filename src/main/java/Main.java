import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jason jason = new Jason();

        System.out.println(Jason.START_MESSAGE);
        System.out.println(Jason.HELP_MESSAGE);

        Scanner sc = new Scanner(System.in);
        Input currentInput = new Input("");

        while (currentInput.type != CommandType.ENDTASK) {
            currentInput = new Input(sc.nextLine());
            switch (currentInput.type) {
                case SHOWLIST -> {
                    System.out.println(jason.taskList);
                }
                case ADDTASK -> {
                    jason.addTask(new Task(currentInput.description));
                    System.out.println("added: " + currentInput.description);
                }
                case MARKTASK -> {
                    jason.markTaskAsComplete(currentInput.number);
                    System.out.println("Nice! I have marked this task as done:");
                    System.out.println(jason.getTask(currentInput.number));
                }
                case UNMARKTASK -> {
                    jason.markTaskAsIncomplete(currentInput.number);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(jason.getTask(currentInput.number));
                }
            }
        }
        System.out.println(Jason.END_MESSAGE);
    }
}
