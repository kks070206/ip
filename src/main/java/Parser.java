import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/** Interprets user commands and creates tasks from add-task commands. */
public class Parser {
    /** Converts a complete user command into an executable command object. */
    public Command parse(String description)
            throws InvalidCommandException, InvalidToDoException, InvalidDeadlineException,
            InvalidEventException {
        CommandType type = parseType(description);
        return switch (type) {
            case ADDTASK -> new AddCommand(parseTask(description));
            case SHOWLIST -> new ListCommand();
            case MARKTASK -> new MarkCommand(parseIndex(description));
            case UNMARKTASK -> new UnmarkCommand(parseIndex(description));
            case DELETETASK -> new DeleteCommand(parseIndex(description));
            case ENDTASK -> new ExitCommand();
            default -> throw new InvalidCommandException();
        };
    }

    /** Determines the command represented by the supplied input. */
    public CommandType parseType(String description) throws InvalidCommandException {
        if (description == null) throw new InvalidCommandException();
        if (description.equals("")) return CommandType.INITIALISE;
        if (description.equals("list")) return CommandType.SHOWLIST;
        if (description.equals("bye")) return CommandType.ENDTASK;

        String[] words = description.split(" ");
        if (words.length > 0 && (words[0].equals("todo")
                || words[0].equals("deadline") || words[0].equals("event"))) {
            return CommandType.ADDTASK;
        }
        if (words.length > 0 && (words[0].equals("mark")
                || words[0].equals("unmark") || words[0].equals("delete"))) {
            if (words.length > 1) {
                try {
                    Integer.parseInt(words[1]);
                    return commandTypeFor(words[0]);
                } catch (NumberFormatException ignored) {
                    // Fall through to the standard invalid-command error.
                }
            }
        }
        throw new InvalidCommandException();
    }

    /** Returns the task index from a mark, unmark, or delete command. */
    public int parseIndex(String description) {
        String[] words = description.split(" ");
        if (words.length < 2) return 0;
        try {
            return Integer.parseInt(words[1]);
        } catch (NumberFormatException ignored) {
            return 0;
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

    private CommandType commandTypeFor(String command) {
        return switch (command) {
            case "mark" -> CommandType.MARKTASK;
            case "unmark" -> CommandType.UNMARKTASK;
            case "delete" -> CommandType.DELETETASK;
            default -> throw new IllegalArgumentException("Unknown command: " + command);
        };
    }
}
