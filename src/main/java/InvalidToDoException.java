public class InvalidToDoException extends InvalidCommandException {
    public InvalidToDoException() {
        super();
    }

    @Override
    public String toString() {
        return "ToDo commands are in the form \"todo {description}\"";
    }
}
