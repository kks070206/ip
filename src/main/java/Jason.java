import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Jason {
    public static void main(String[] args) {
        String greetMessage = "Hello! My name is Jason, inspired by JSON files used by software engineers.";
        String helpMessage = "How may I help you today?";
        String endMessage = "Goodbye! Hope to see you again.";

        System.out.println(greetMessage);
        System.out.println(helpMessage);

        List<String> list = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                for (int i = 1; i <= list.size(); i++) {
                    System.out.println(i + ". " + list.get(i - 1));
                }
                input = sc.nextLine();
                continue;
            }
            list.add(input);
            System.out.println("added: " + input);
            input = sc.nextLine();
        }
        System.out.println(endMessage);
    }
}
