package jason.exception;

public class InvalidDeadlineException extends InvalidCommandException {
    public InvalidDeadlineException() {
        super();
    }

    @Override
    public String toString() {
        return "Deadline commands are in the form \"deadline {description} /by {YYYY-MM-DD HH:MM}\"";
    }
}
