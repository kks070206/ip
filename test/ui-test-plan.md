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
java -cp out/production/ip Main
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
