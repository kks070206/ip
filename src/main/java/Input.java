import java.util.Arrays;
import java.util.List;
import java.time.format.DateTimeParseException;

public class Input {
    private final String description;
    private final String[] parsedInput;
    private final Jason jason;
    private final Ui ui;
    private CommandType type;
    private int number;

    public Input(String description, Jason jason) throws InvalidCommandException{
        this(description, jason, new Ui());
    }

    public Input(String description, Jason jason, Ui ui) throws InvalidCommandException{
        this.description = description;
        this.jason = jason;
        this.ui = ui;
        parsedInput = description.split(" ");

        if (description.equals("")) {
            this.type = CommandType.INITIALISE;
        }

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
        } else if (parsedInput[0].equals("delete")) {
            try {
                Integer.parseInt(parsedInput[1]);
                this.number = Integer.parseInt(parsedInput[1]);
                this.type = CommandType.DELETETASK;
            } catch (NumberFormatException _) {
            }
        }

        if (this.type == null) {
            throw new InvalidCommandException();
        }

    }

    public boolean terminate() {
        return this.type == CommandType.ENDTASK;
    }

    public boolean verifyEventTask() {
        List<String> list = Arrays.asList(parsedInput);
        return parsedInput.length >= 6 && list.contains("/from") && list.contains("/to");
    }

    public Task createTask() throws InvalidToDoException, InvalidDeadlineException, InvalidEventException {

        switch (parsedInput[0]) {
            case "todo" -> {
                if (parsedInput.length < 2) throw new InvalidToDoException();
                return new ToDo(description.split(" ", 2)[1]);
            }
            case "deadline" -> {
                if(parsedInput.length < 4 || !Arrays.asList(parsedInput).contains("/by")) {
                    throw new InvalidDeadlineException();
                }
                String[] parsedInputForDeadline = description.split("deadline\\s+|\\s+/by\\s+", 3);
                String taskDescription = parsedInputForDeadline[1];
                String deadline = parsedInputForDeadline[2];
                try {
                    return new Deadline(taskDescription, deadline);
                } catch (DateTimeParseException e) {
                    throw new InvalidDeadlineException();
                }
            }
            case "event" -> {
                if (!verifyEventTask()) throw new InvalidEventException();
                String[] parsedInputForEvent = description.split("event\\s+|\\s+/from\\s+|\\s+/to\\s+", 4);
                String taskDescription = parsedInputForEvent[1];
                String startTime = parsedInputForEvent[2];
                String endTime = parsedInputForEvent[3];
                try {
                    return new Event(taskDescription, startTime, endTime);
                } catch (DateTimeParseException e) {
                    throw new InvalidEventException();
                }
            }
        }
        // will not reach
        return new ToDo("");
    }

    public void execute() throws InvalidToDoException, InvalidDeadlineException, InvalidEventException, InvalidIndexException {
        switch (this.type) {
            case SHOWLIST -> {
                ui.showTaskList(this.jason.taskList);
            }
            case ADDTASK -> {
                Task newTask = this.createTask();
                jason.addTask(newTask);
                ui.showAddedTask(newTask);
            }
            case MARKTASK -> {
                jason.markTaskAsComplete(this.number);
                ui.showMarkedComplete(this.jason.getTask(this.number));
            }
            case UNMARKTASK -> {
                jason.markTaskAsIncomplete(this.number);
                ui.showMarkedIncomplete(this.jason.getTask(this.number));
            }
            case DELETETASK -> {
                Task deletedTask = this.jason.getTask(this.number);
                jason.deleteTask(this.number);
                ui.showDeletedTask(deletedTask, this.jason.size());

            }
        }
    }
}
