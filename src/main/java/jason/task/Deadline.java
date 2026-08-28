package jason.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm", Locale.ENGLISH);
    private LocalDateTime deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = parseDate(deadline);
    }

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        if (deadline == null) throw new IllegalArgumentException("A deadline date cannot be null.");
        this.deadline = deadline;
    }

    /**
     * Returns the deadline for persistence.
     */
    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(),
                this.deadline.format(DISPLAY_FORMAT));
    }

    /** Parses the required ISO date format. */
    private static LocalDateTime parseDate(String date) {
        if (date == null || date.isBlank()) {
            throw new DateTimeParseException("Date cannot be empty", date == null ? "null" : date, 0);
        }
        String value = date.trim();
        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(value, INPUT_FORMAT);
            } catch (DateTimeParseException ignored) {
                return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            }
        }
    }
}
