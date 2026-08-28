# UI Test Plan

This file records the command, console input, and exact expected stdout for
each command-line UI test. Run it with:

```bash
python3 .codex/skills/test-ui/scripts/run_ui_tests.py test/ui-test-plan.md
```

Timeout seconds: 10

## Test case: Start and exit

### Aim

Verify that the chatbot starts and exits when the user enters `bye`.

### Command

```shell
java -cp build/classes/java/main jason.Main
```

### Input

```text
bye
```

### Expected output

```text
Hello! My name is Jason, inspired by JSON files used by software engineers.
How may I help you today?
_______
_______
Goodbye! Hope to see you again.
```

Add further `## Test case:` sections below as UI behavior is implemented. Each
case must include an aim, command, input, and exact expected output.

## Test case: Find matching tasks

### Aim

Verify that `find` displays matching task descriptions in their list order and
that a later command can still be processed.

### Command

```shell
java -cp build/classes/java/main jason.Main
```

### Input

```text
find lunch
bye
```

### Expected output

```text
Hello! My name is Jason, inspired by JSON files used by software engineers.
How may I help you today?
_______
Here are the matching tasks in your list:
1. [T] [ ] eat lunch
2. [E] [ ] lunch (from: Aug 29 2026 16:00 to: Aug 29 2026 18:00)
_______
_______
_______
Goodbye! Hope to see you again.
```
