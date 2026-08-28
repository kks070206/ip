public class Main {
    public static void main(String[] args) {
        Jason jason = new Jason();
        Ui ui = new Ui();
        ui.showWelcome();
        boolean isRunning = true;

        while (isRunning) {
            try {
                Input currentInput = new Input("", jason, ui);

                while (!currentInput.terminate()) {
                    currentInput = new Input(ui.readCommand(), jason, ui);
                    currentInput.execute();
                }

                isRunning = false;
            } catch (Exception e) {
                ui.showError(e);
            }
        }

        ui.showGoodbye();
    }
}
