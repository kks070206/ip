public class Input {
    private final String description;
    private final Jason jason;
    private final Ui ui;
    private final Parser parser;
    private CommandType type;
    private int number;

    public Input(String description, Jason jason) throws InvalidCommandException{
        this(description, jason, new Ui());
    }

    public Input(String description, Jason jason, Ui ui) throws InvalidCommandException{
        this.description = description;
        this.jason = jason;
        this.ui = ui;
        this.parser = new Parser();
        this.type = parser.parseType(description);
        this.number = parser.parseIndex(description);

    }

    public boolean terminate() {
        return this.type == CommandType.ENDTASK;
    }

    public Task createTask() throws InvalidToDoException, InvalidDeadlineException, InvalidEventException {
        return parser.parseTask(description);
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
