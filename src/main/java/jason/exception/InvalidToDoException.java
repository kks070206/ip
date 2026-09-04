package jason.exception;

/**
 * Indicates that a todo command has invalid syntax.
 */
public class InvalidToDoException extends InvalidCommandException {
    /**
     * Creates an invalid-todo exception.
     */
    public InvalidToDoException() {
        super();
    }

    /**
     * Returns the expected todo command format.
     *
     * @return expected todo command format.
     */
    @Override
    public String toString() {
        return "ToDo commands are in the form \"todo {description}\"";
    }
}
