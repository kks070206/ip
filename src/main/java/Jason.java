import java.util.Scanner;

public class Jason {
    public static void main(String[] args) {
        String greetMessage = "Hello! My name is Jason, inspired by JSON files used by software engineers.";
        String helpMessage = "How may I help you today?";
        String endMessage = "Goodbye! Hope to see you again.";
        System.out.println(greetMessage);
        System.out.println(helpMessage);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            System.out.println(input);
            input = sc.nextLine();
        }
        System.out.println(endMessage);
    }
}
