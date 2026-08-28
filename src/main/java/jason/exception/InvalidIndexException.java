package jason.exception;

/** Indicates that a requested task index is not present. */
public class InvalidIndexException extends Exception {
    /** Creates an invalid-index exception. */
    public InvalidIndexException() {
        super();
    }

    @Override
    /** Returns the invalid-index error message. */
    public String toString() {
        return "No such index in list.";
    }
}
