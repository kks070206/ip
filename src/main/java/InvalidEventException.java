public class InvalidEventException extends InvalidCommandException {
    public InvalidEventException() {
        super();
    }

    @Override
    public String toString() {
        return "Event commands are in the form \"event {description} /from {YYYY-MM-DD HH:MM} /to {YYYY-MM-DD HH:MM}\"";
    }
}
