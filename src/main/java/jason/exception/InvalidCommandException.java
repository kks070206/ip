package jason.exception;

/** Indicates that a user command is not recognized. */
public class InvalidCommandException extends Exception {
    /** Creates an invalid-command exception. */
    public InvalidCommandException() {
        super();
    }

    @Override
    /** Returns guidance for correcting the command. */
    public String toString() {
        return "Invalid command. Please refer to the documents on how to use me.";
    }
}
