import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jason jason = new Jason();

        System.out.println(Jason.START_MESSAGE);
        System.out.println(Jason.HELP_MESSAGE);

        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            try {
                Input currentInput = new Input("", jason);

                while (!currentInput.terminate()) {
                    currentInput = new Input(sc.nextLine(), jason);
                    currentInput.execute();
                }

                isRunning = false;
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        System.out.println(Jason.END_MESSAGE);
    }
}
