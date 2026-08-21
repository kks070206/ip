public class InvalidEventException extends Exception {
    public InvalidEventException() {
        super();
    }

    @Override
    public String toString() {
        return "Event commands are in the form \"event {description} /from {starting time} /to {ending time}\"";
    }
}
