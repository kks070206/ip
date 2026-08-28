package jason.exception;

public class InvalidIndexException extends Exception {
    public InvalidIndexException() {
        super();
    }

    @Override
    public String toString() {
        return "No such index in list.";
    }
}
