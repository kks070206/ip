public class InvalidCommandException extends Exception {
    public InvalidCommandException() {
        super();
    }

    @Override
    public String toString() {
        return "Invalid command. Please refer to the documents on how to use me.";
    }
}
