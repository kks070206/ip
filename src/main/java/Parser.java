import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/** Interprets user commands and creates tasks from add-task commands. */
public class Parser {
    /** Converts a complete user command into an executable command object. */
    public Command parse(String description)
            throws InvalidCommandException, InvalidToDoException, InvalidDeadlineException,
            InvalidEventException {
        if (description == null) throw new InvalidCommandException();
        String[] words = description.split(" ");
        if (words.length == 0 || words[0].isEmpty()) throw new InvalidCommandException();
        return switch (words[0]) {
            case "todo", "deadline", "event" -> new AddCommand(parseTask(description));
            case "list" -> new ListCommand();
            case "bye" -> new ExitCommand();
            case "mark" -> new MarkCommand(parseIndex(description));
            case "unmark" -> new UnmarkCommand(parseIndex(description));
            case "delete" -> new DeleteCommand(parseIndex(description));
            default -> throw new InvalidCommandException();
        };
    }

    /** Returns the task index from a mark, unmark, or delete command. */
    public int parseIndex(String description) {
        String[] words = description.split(" ");
        if (words.length < 2) throw new IllegalArgumentException("A task index is required.");
        try {
            return Integer.parseInt(words[1]);
        } catch (NumberFormatException ignored) {
            throw new IllegalArgumentException("The task index must be a number.");
        }
    }

    /** Creates the task represented by an add-task command. */
    public Task parseTask(String description)
            throws InvalidToDoException, InvalidDeadlineException, InvalidEventException {
        String[] parsedInput = description.split(" ");
        if (parsedInput.length == 0) throw new InvalidToDoException();

        switch (parsedInput[0]) {
            case "todo" -> {
                if (parsedInput.length < 2) throw new InvalidToDoException();
                return new ToDo(description.split(" ", 2)[1]);
            }
            case "deadline" -> {
                if (parsedInput.length < 4 || !Arrays.asList(parsedInput).contains("/by")) {
                    throw new InvalidDeadlineException();
                }
                String[] parts = description.split("deadline\\s+|\\s+/by\\s+", 3);
                try {
                    return new Deadline(parts[1], parts[2]);
                } catch (DateTimeParseException e) {
                    throw new InvalidDeadlineException();
                }
            }
            case "event" -> {
                List<String> words = Arrays.asList(parsedInput);
                if (parsedInput.length < 6 || !words.contains("/from") || !words.contains("/to")) {
                    throw new InvalidEventException();
                }
                String[] parts = description.split("event\\s+|\\s+/from\\s+|\\s+/to\\s+", 4);
                try {
                    return new Event(parts[1], parts[2], parts[3]);
                } catch (DateTimeParseException e) {
                    throw new InvalidEventException();
                }
            }
            default -> throw new InvalidToDoException();
        }
    }

}
