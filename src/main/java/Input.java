public class Input {
    public String description;
    public String[] parsedInput;
    public CommandType type;
    public int number = -1;

    public Input(String description) {
        this.description = description;
        this.parsedInput = description.split(" ");
        this.type = CommandType.ADDTASK;

        if (description.equals("list")) {
            this.type = CommandType.SHOWLIST;
        } else if (description.equals("bye")) {
            this.type = CommandType.ENDTASK;
        } else {
            for (String s : parsedInput) {
                try {
                    Integer.parseInt(s);
                    switch (parsedInput[0]) {
                        case "mark" -> this.type = CommandType.MARKTASK;
                        case "unmark" -> this.type = CommandType.UNMARKTASK;
                        default -> {
                            return;
                        }
                    }
                    this.number = Integer.parseInt(s);
                    break;
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
    }
}
