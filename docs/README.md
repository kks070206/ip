# Jason User Guide

Jason is a command-line task manager with an optional JavaFX graphical
interface.

## Adding todos

Add a normal todo with:

```text
todo <description>
```

Example:

```text
todo read book
```

Add an unscheduled todo with a fixed duration using `/for`:

```text
todo <description> /for <duration>
```

The accepted duration formats are `2h`, `90m`, and `1h 30m`. Durations must
be between 1 minute and 24 hours. The application displays and stores the
duration in normalized form, such as `1h 30m`.

Example:

```text
todo read sales report /for 90m
```

Expected output:

```text
Added to your task list: [T] [ ] read sales report (for: 1h 30m)
```

Duration todos are descriptive only. Jason does not start a timer or track
the actual time spent. They can be listed, found by description, marked,
unmarked, and deleted like normal todos.

Invalid examples include `todo read report /for`, `todo read report /for 0m`,
and `todo read report /for 25h`.

## Adding deadlines

Add a deadline with:

```text
deadline <description> /by <YYYY-MM-DD HH:MM>
```

Example:

```text
deadline submit report /by 2026-10-15 18:00
```
