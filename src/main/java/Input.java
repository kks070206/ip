public class Input {
    private final String description;
    private final String[] parsedInput;
    private final Jason jason;
    private CommandType type;
    private int number = -1;

    public Input(String description, Jason jason) {
        this.description = description;
        this.jason = jason;
        parsedInput = description.split(" ");

        if (parsedInput[0].equals("todo") || parsedInput[0].equals("deadline") || parsedInput[0].equals("event")) {
            this.type = CommandType.ADDTASK;
        }

        if (description.equals("list")) {
            this.type = CommandType.SHOWLIST;
        } else if (description.equals("bye")) {
            this.type = CommandType.ENDTASK;
        } else if (parsedInput[0].equals("mark")) {
            try {
                Integer.parseInt(parsedInput[1]);
                this.number = Integer.parseInt(parsedInput[1]);
                this.type = CommandType.MARKTASK;
            } catch (NumberFormatException _) {
            }
        } else if (parsedInput[0].equals("unmark")) {
            try {
                Integer.parseInt(parsedInput[1]);
                this.number = Integer.parseInt(parsedInput[1]);
                this.type = CommandType.UNMARKTASK;
            } catch (NumberFormatException _) {
            }
        }

    }

    public boolean terminate() {
        return this.type == CommandType.ENDTASK;
    }

    public Task createTask() {
        switch (parsedInput[0]) {
            case "todo" -> {
                return new ToDo(description.split(" ", 2)[1]);
            }
            case "deadline" -> {
                String[] parsedInputForDeadline = description.split("deadline\\s+|\\s+/by\\s+", 3);
                String taskDescription = parsedInputForDeadline[1];
                String deadline = parsedInputForDeadline[2];
                return new Deadline(taskDescription, deadline);
            }
            case "event" -> {
                String[] parsedInputForEvent = description.split("event\\s+|\\s+/from\\s+|\\s+/to\\s+", 4);
                String taskDescription = parsedInputForEvent[1];
                String startTime = parsedInputForEvent[2];
                String endTime = parsedInputForEvent[3];
                return new Event(taskDescription, startTime, endTime);
            }
        }
        // will not reach
        return new ToDo("");
    }

    public void execute() {
        switch (this.type) {
            case SHOWLIST -> {
                System.out.println(this.jason.taskList);
            }
            case ADDTASK -> {
                Task newTask = this.createTask();
                jason.addTask(newTask);
                System.out.println("Added: " + newTask);
            }
            case MARKTASK -> {
                jason.markTaskAsComplete(this.number);
                System.out.println("Nice! I have marked this task as done:");
                System.out.println(this.jason.getTask(this.number));
            }
            case UNMARKTASK -> {
                jason.markTaskAsIncomplete(this.number);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(this.jason.getTask(this.number));
            }
        }
    }
}
